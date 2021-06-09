package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.model.Backoffice;
import net.pelozo.FinalTPLab5DB2.model.Client;
import net.pelozo.FinalTPLab5DB2.model.Invoice;
import net.pelozo.FinalTPLab5DB2.model.Tariff;
import net.pelozo.FinalTPLab5DB2.repository.BackofficeRepository;
import net.pelozo.FinalTPLab5DB2.repository.ClientRepository;
import net.pelozo.FinalTPLab5DB2.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BackofficeService {

    @Autowired
    BackofficeRepository backofficeRepository;
    @Autowired
    InvoiceService invoiceService;


    public Backoffice login(String username, String password) {
        return backofficeRepository.findByUsernameAndPassword(username, password);
    }

    public Page<Invoice> getUnpaidInvoicesByClientAndResidence(long clientId, long residenceId, Pageable pageable) {
        return invoiceService.findUnpaidInvoicesByClientAndResidence(clientId, residenceId, pageable);
    }
}
