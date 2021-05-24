package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.model.Invoice;
import net.pelozo.FinalTPLab5DB2.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Invoice add(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public List<Invoice> getAll() {
        return invoiceRepository.findAll();
    }

    public List<Invoice> getByClientId(long id) {
        return invoiceRepository.findAll()
                .stream().
                filter(invoice -> invoice.getResidence().getClient().getId() == id).
                collect(Collectors.toList());
    }
}