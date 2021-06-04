package net.pelozo.FinalTPLab5DB2.controller;

import net.pelozo.FinalTPLab5DB2.dto.ClientDto;
import net.pelozo.FinalTPLab5DB2.exception.ClientNotExistsException;
import net.pelozo.FinalTPLab5DB2.model.Client;
import net.pelozo.FinalTPLab5DB2.model.Intake;
import net.pelozo.FinalTPLab5DB2.model.Invoice;
import net.pelozo.FinalTPLab5DB2.projections.MeasurementProjection;
import net.pelozo.FinalTPLab5DB2.service.ClientService;
import net.pelozo.FinalTPLab5DB2.service.InvoiceService;
import net.pelozo.FinalTPLab5DB2.service.MeasurementService;
import net.pelozo.FinalTPLab5DB2.utils.EntityURLBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;
import static net.pelozo.FinalTPLab5DB2.utils.MyResponse.response;


@RestController
@RequestMapping("/clients")
public class ClientController {



    private final ClientService clientService;
    private final InvoiceService invoiceService;
    private final ModelMapper modelMapper;
    private final MeasurementService measurementService;


    @Autowired
    public ClientController(ClientService clientService, InvoiceService invoiceService, ModelMapper modelMapper, MeasurementService measurementService) {
        this.clientService = clientService;
        this.invoiceService = invoiceService;
        this.modelMapper = modelMapper;
        this.measurementService = measurementService;
    }
    @PreAuthorize(value= "hasAuthority('BACKOFFICE')")
    @GetMapping
    public ResponseEntity<List<ClientDto>> getAll(Pageable pageable){
        Page<ClientDto> page =  clientService.getAll(pageable).map(ClientDto::from);
        return response(page)
                .body(page.getContent());
    }

    @PreAuthorize(value= "hasAuthority('BACKOFFICE')")
    @PostMapping
    public ResponseEntity add(@RequestBody Client client){
        Client c = clientService.add(client);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(EntityURLBuilder.buildURL("clients",c.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .body("");
    }
    @PreAuthorize(value= "hasAuthority('BACKOFFICE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) throws ClientNotExistsException {
        clientService.deleteById(id);
        return ResponseEntity.ok("Client Successfully Deleted! ");
    }


    @PreAuthorize(value= "hasAuthority('BACKOFFICE') or authentication.principal.id.equals(#id)")
    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getById(@PathVariable Long id) throws ClientNotExistsException {
        //return ResponseEntity.ok(ClientDto.from(clientService.getById(id)));
        ClientDto cd = modelMapper.map(clientService.getById(id),ClientDto.class);

        return ResponseEntity.ok(cd);
    }
//--------------------------------------------------------------------------------------------------
    //revisar como puedo hacer esto
//    @PostMapping
//    public ResponseEntity<Invoice> add(@RequestBody Invoice invoice){
//        Invoice in = invoiceService.add(invoice);
//
//        return ResponseEntity.created(ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(in.getId())
//                .toUri())
//                .build();
//    }


    //consulta facturas por cliente
    @GetMapping("/{id}/invoices")
    @PreAuthorize(value= "hasAuthority('BACKOFFICE') or authentication.principal.id.equals(#id)")
    public ResponseEntity<List<Invoice>> getInvoices(@PathVariable long id,
                                                     @RequestParam @DateTimeFormat(pattern="MM-yyyy") Date startDate,
                                                     @RequestParam @DateTimeFormat(pattern="MM-yyyy") Date endDate,
                                                     Pageable pageable,
                                                     Principal principal){
        Page<Invoice> invoices = invoiceService.getByClientIdAndDate(id, startDate, endDate, pageable);
        return response(invoices)
                .body(invoices.getContent());
    }

    @PreAuthorize(value= "hasAuthority('BACKOFFICE') or authentication.principal.id.equals(#id)")
    @GetMapping("/{id}/invoices/unpaid")
    public ResponseEntity<List<Invoice>> getUnpaidInvoices(@PathVariable long id,
                                                                 Pageable pageable,
                                                                 Principal principal) {
        Page<Invoice> invoices = invoiceService.getByClientUnpaid(id, pageable);
        return response(invoices)
                .body(invoices.getContent());
    }


    //consultar facturas por fecha

    //consultar facturas impagas

    //consultar consumo por rango de fechas
    @GetMapping("/{id}/intake")
    public ResponseEntity<Optional<Intake>> getIntakeByDateRange ( @PathVariable long id,
    @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime from,
    @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime to){

        Optional<Intake> intake = measurementService.getIntakeByRangeOfDates(id, from, to);

        return ResponseEntity.status(intake.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(intake);
    }


    //consultar mediciones por rango de fecha
    @GetMapping("/{id}/measurements")
    public ResponseEntity<List<MeasurementProjection>> getMeasurementsByDateRange ( @PathVariable long id,
    @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime from,
    @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime to,
    Pageable pageable)
    {
        Page<MeasurementProjection> measurements = measurementService.getMeasurementsByDateRange(id, from, to, pageable);
        return response(measurements)
                .body(measurements.getContent());
    }



    }

