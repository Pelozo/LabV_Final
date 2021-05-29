package net.pelozo.FinalTPLab5DB2.controller;

import net.pelozo.FinalTPLab5DB2.dto.ClientDto;
import net.pelozo.FinalTPLab5DB2.exception.ClientNotExistsException;
import net.pelozo.FinalTPLab5DB2.model.Client;
import net.pelozo.FinalTPLab5DB2.model.Invoice;
import net.pelozo.FinalTPLab5DB2.service.ClientService;
import net.pelozo.FinalTPLab5DB2.service.InvoiceService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.Principal;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/clients")
public class ClientController {


    private final ClientService clientService;
    private final InvoiceService invoiceService;
    private final ModelMapper modelMapper;

    @Autowired
    public ClientController(ClientService clientService, InvoiceService invoiceService, ModelMapper modelMapper) {
        this.clientService = clientService;
        this.invoiceService = invoiceService;
        this.modelMapper = modelMapper;

    }
    @PreAuthorize(value= "hasAuthority('BACKOFFICE')")
    @GetMapping
    public ResponseEntity<Page<ClientDto>> getAll(Pageable pageable){
        Page<ClientDto> page =  clientService.getAll(pageable).map(ClientDto::from);
        return ResponseEntity
                .status(page.isEmpty() ? HttpStatus.NO_CONTENT: HttpStatus.OK)
                .body(page);
    }

    @PreAuthorize(value= "hasAuthority('BACKOFFICE')")
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
    @PreAuthorize(value= "hasAuthority('BACKOFFICE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) throws ClientNotExistsException {
        clientService.deleteById(id);
        return ResponseEntity.ok().body("Client successfully deleted! ");
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
    public ResponseEntity<Page<List<Invoice>>> getInvoices(@PathVariable long id,
                                                     @RequestParam @DateTimeFormat(pattern="MM-yyyy") Date startDate,
                                                     @RequestParam @DateTimeFormat(pattern="MM-yyyy") Date endDate,
                                                     Pageable pageable,
                                                     Principal principal){
        Page<List<Invoice>> invoices = invoiceService.getByClientIdAndDate(id, startDate, endDate, pageable);
        return ResponseEntity
                .status(invoices.isEmpty() ? HttpStatus.NO_CONTENT: HttpStatus.OK)
                .body(invoices);
    }

    @PreAuthorize(value= "hasAuthority('BACKOFFICE') or authentication.principal.id.equals(#id)")
    @GetMapping("/{id}/invoices/unpaid")
    public ResponseEntity<Page<List<Invoice>>> getUnpaidInvoices(@PathVariable long id,
                                                                 Pageable pageable,
                                                                 Principal principal){
        Page<List<Invoice>> invoices = invoiceService.getByClientUnpaid(id,pageable);
        return ResponseEntity
                .status(invoices.isEmpty() ? HttpStatus.NO_CONTENT: HttpStatus.OK)
                .body(invoices);
    }


    //consultar facturas por fecha

    //consultar facturas impagas

    //consultar consumo por rango de fechas

    //consultar mediciones por rango de fecha


}
