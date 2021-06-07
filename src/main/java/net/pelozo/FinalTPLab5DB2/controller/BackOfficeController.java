package net.pelozo.FinalTPLab5DB2.controller;

import lombok.SneakyThrows;
import net.pelozo.FinalTPLab5DB2.exception.IdViolationException;
import net.pelozo.FinalTPLab5DB2.exception.NonExistentResourceException;
import net.pelozo.FinalTPLab5DB2.model.Invoice;
import net.pelozo.FinalTPLab5DB2.model.Measurement;
import net.pelozo.FinalTPLab5DB2.model.Tariff;
import net.pelozo.FinalTPLab5DB2.service.BackofficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/backoffice")
public class BackOfficeController {

    private final TariffController tariffController;
    private final BackofficeService backofficeService;
    private final MeasurementController measurementController;

    @Autowired
    public BackOfficeController(TariffController tariffController, BackofficeService backofficeService, MeasurementController measurementController) {
        this.tariffController = tariffController;
        this.backofficeService = backofficeService;
        this.measurementController = measurementController;
    }

    /*
    @GetMapping("/tariffs/{id}")
    public ResponseEntity<Tariff> getTariff(@PathVariable long id){
        return tariffController.getById(id);
    }

     */
    @PreAuthorize(value= "hasAuthority('BACKOFFICE')")
    @GetMapping("/tariffs")
    public ResponseEntity<List<Tariff>> getAllTariffs(Pageable pageable){
        return tariffController.getAll(pageable);
    }


    //alta tarifa
    @PreAuthorize(value= "hasAuthority('BACKOFFICE')")
    @PostMapping("/tariffs")
    public ResponseEntity<String> addTariff(@RequestBody Tariff tariff){
        return tariffController.addTariff(tariff);
    }

    //baja tarifa
    @PreAuthorize(value= "hasAuthority('BACKOFFICE')")
    @DeleteMapping("/tariffs/{id}")
    public ResponseEntity<String> deleteTariffById(@PathVariable long id) throws NonExistentResourceException {
        return tariffController.deleteById(id);
    }

    //modificacion tarifa

    @PreAuthorize(value= "hasAuthority('BACKOFFICE')")
    @PutMapping("/tariffs/{id}")
    public ResponseEntity<Tariff> updateTariff(@PathVariable long id, @RequestBody Tariff tariff) throws NonExistentResourceException, IdViolationException {
        return tariffController.updateTariff(id, tariff);
    }


    @PreAuthorize(value= "hasAuthority('BACKOFFICE')")
    @GetMapping("/clients/{clientId}/residences/{residenceId}/invoices/unpaid")
    public ResponseEntity<List<Invoice>> getUnpaidInvoicesByClientAndResident(@PathVariable long clientId,@PathVariable long residenceId){
            List<Invoice> unpaidInvoices = backofficeService.getUnpaidInvoicesByClientAndResidence(clientId,residenceId);

            return ResponseEntity
                    .status(unpaidInvoices.isEmpty()? HttpStatus.NO_CONTENT:HttpStatus.OK)
                    .body(unpaidInvoices);
    }

//    @PreAuthorize(value= "hasAuthority('BACKOFFICE')")
//    @GetMapping("/consumers/topTen")
//    public ResponseEntity<List<>> getTopTenConsumers(){
//
//    }


    @PreAuthorize(value= "hasAuthority('BACKOFFICE')")
    @GetMapping("/residences/{residenceId}/measurements")
    public ResponseEntity<List<Measurement>> getMeasurementsByResidenceAndRangeOfDates(@PathVariable long residenceId,
                                                                                       @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime from,
                                                                                       @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime to,
                                                                                       Pageable pageable){
        Page<Measurement> measurements = measurementController.getMeasurementsByResidenceAndRangeOfDates(residenceId,from,to,pageable);
        return  ResponseEntity
                .status(measurements.isEmpty()?HttpStatus.NO_CONTENT:HttpStatus.OK)
                .body(measurements.getContent());
    }

}
