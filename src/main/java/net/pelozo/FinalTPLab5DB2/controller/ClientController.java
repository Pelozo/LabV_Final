package net.pelozo.FinalTPLab5DB2.controller;

import net.pelozo.FinalTPLab5DB2.dto.ClientDto;
import net.pelozo.FinalTPLab5DB2.exception.ClientNotExistsException;
import net.pelozo.FinalTPLab5DB2.model.Client;
import net.pelozo.FinalTPLab5DB2.model.Invoice;
import net.pelozo.FinalTPLab5DB2.model.Measurement;
import net.pelozo.FinalTPLab5DB2.service.ClientService;
import net.pelozo.FinalTPLab5DB2.service.InvoiceService;

import net.pelozo.FinalTPLab5DB2.service.MeasurementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Date;


@RestController
@RequestMapping("/clients")
public class ClientController {


    private ClientService clientService;
    private InvoiceService invoiceService;
    private ModelMapper modelMapper;
    private MeasurementService measurementService;

    @Autowired
    public ClientController(ClientService clientService, InvoiceService invoiceService, ModelMapper modelMapper, MeasurementService measurementService) {
        this.clientService = clientService;
        this.invoiceService = invoiceService;
        this.modelMapper = modelMapper;
        this.measurementService = measurementService;
    }

    @GetMapping
    public ResponseEntity<Page<ClientDto>> getAll(Pageable pageable){
        Page<ClientDto> page =  clientService.getAll(pageable).map(ClientDto::from);
        return ResponseEntity
                .status(page.isEmpty() ? HttpStatus.NO_CONTENT: HttpStatus.OK)
                .body(page);
    }

    @PostMapping
    public ResponseEntity<Client> add(@RequestBody Client client){
        Client c = clientService.add(client);

        return ResponseEntity.created(ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(c.getId())
                    .toUri())
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) throws ClientNotExistsException {
        clientService.deleteById(id);
        return ResponseEntity.ok().body("Client successfully deleted! ");
    }

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
    //TODO: hacer que esta funcion acepte parametros para revisar por fecha y por impagas
    @GetMapping("/{id}/invoices")
    public ResponseEntity<Page<Invoice>> getInvoices(@PathVariable long id, Pageable pageable){
        Page<Invoice> invoices = invoiceService.getByClientId(id, pageable);
        return ResponseEntity.status(invoices.isEmpty()? HttpStatus.NO_CONTENT:HttpStatus.OK).body(invoices);
    }


    //consultar facturas por fecha

    //consultar facturas impagas

    //consultar consumo por rango de fechas

    //consultar mediciones por rango de fecha
//    @GetMapping("/{id}/measurements")
//    public ResponseEntity<Page<Measurement>> getMeasurementsByDateRange(@PathVariable long id,
//                                                                        @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime from,
//                                                                        @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime to,
//                                                                        Pageable pageable){
//
//        Page<Measurement> measurements = measurementService.getMeasurementsByRangeOfDates(id,from,to,pageable);
//
//        return ResponseEntity.status(measurements.isEmpty()?HttpStatus.NO_CONTENT:HttpStatus.OK).body(measurements);
//    }


}
