package net.pelozo.FinalTPLab5DB2.controller;

import net.pelozo.FinalTPLab5DB2.model.Invoice;
import net.pelozo.FinalTPLab5DB2.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static net.pelozo.FinalTPLab5DB2.utils.MyResponse.response;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public ResponseEntity<List<Invoice>> getInvoices(Pageable pageable){
        Page<Invoice> invoices = invoiceService.getAll(pageable);
        return response(invoices);
    }
/*
    @PreAuthorize(value= "hasAuthority('BACKOFFICE')")
    //@GetMapping("/clients/{clientId}/residences/{residenceId}/invoices/unpaid")
    public ResponseEntity<List<Invoice>> getUnpaidInvoicesByClientAndResidence(@PathVariable long clientId,@PathVariable long residenceId, Pageable pageable){
        Page<Invoice> unpaidInvoices = backofficeService.getUnpaidInvoicesByClientAndResidence(clientId,residenceId, pageable);

        return ResponseEntity
                .status(unpaidInvoices.isEmpty()? HttpStatus.NO_CONTENT:HttpStatus.OK)
                .body(unpaidInvoices.getContent());
    }
*/

}
