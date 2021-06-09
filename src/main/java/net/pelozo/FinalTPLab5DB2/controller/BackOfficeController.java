package net.pelozo.FinalTPLab5DB2.controller;

import lombok.SneakyThrows;
import net.pelozo.FinalTPLab5DB2.exception.IdViolationException;
import net.pelozo.FinalTPLab5DB2.exception.NonExistentResourceException;
import net.pelozo.FinalTPLab5DB2.model.Invoice;
import net.pelozo.FinalTPLab5DB2.model.Measurement;
import net.pelozo.FinalTPLab5DB2.model.Tariff;
import net.pelozo.FinalTPLab5DB2.model.dto.MeasurementsDto;
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

    @PreAuthorize(value= "hasAuthority('BACKOFFICE')")
    @GetMapping("/clients/{clientId}/residences/{residenceId}/invoices/unpaid")
    public ResponseEntity<List<Invoice>> getUnpaidInvoicesByClientAndResidence(@PathVariable long clientId,@PathVariable long residenceId, Pageable pageable){
            Page<Invoice> unpaidInvoices = backofficeService.getUnpaidInvoicesByClientAndResidence(clientId,residenceId, pageable);

            return ResponseEntity
                    .status(unpaidInvoices.isEmpty()? HttpStatus.NO_CONTENT:HttpStatus.OK)
                    .body(unpaidInvoices.getContent());
    }

//    @PreAuthorize(value= "hasAuthority('BACKOFFICE')")
//    @GetMapping("/consumers/topTen")
//    public ResponseEntity<List<>> getTopTenConsumers(){
//
//    }


    @PreAuthorize(value= "hasAuthority('BACKOFFICE')")
    @GetMapping("/residences/{residenceId}/measurements")
    public ResponseEntity<List<MeasurementsDto>> getMeasurementsByResidenceAndRangeOfDates(@PathVariable long residenceId,
                                                                                           @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime from,
                                                                                           @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime to,
                                                                                           Pageable pageable){
        Page<MeasurementsDto> measurements = measurementController.getMeasurementsByResidenceAndRangeOfDates(residenceId,from,to,pageable);
        return  ResponseEntity
                .status(measurements.isEmpty()?HttpStatus.NO_CONTENT:HttpStatus.OK)
                .body(measurements.getContent());
    }

}
