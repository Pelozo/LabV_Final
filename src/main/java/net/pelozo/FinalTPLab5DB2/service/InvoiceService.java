package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.model.Invoice;
import net.pelozo.FinalTPLab5DB2.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InvoiceService {


    private final  InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Page<Invoice> getAll(Pageable pageable) {
        return invoiceRepository.findAll(pageable);
    }

    public Page<Invoice> getByClientId(long id, Pageable pageable) {

        return invoiceRepository.findByResidence_Client_Id(id, pageable);
        /*return invoiceRepository.findAll()
                .stream().
                filter(invoice -> invoice.getResidence().getClient().getId() == id).
                collect(Collectors.toList());*/
    }

    public Page<Invoice> getByClientIdAndDate(long id, Date startDate, Date endDate, Pageable pageable) {
        //return invoiceRepository.findAllByResidence_Client_Id(id);
        //return invoiceRepository.findAllByResidence_Client_IdAndIssueDateLessThanEqualAndIssueDateGreaterThanEqual(id, startDate,endDate,pageable);
        return invoiceRepository.findByClientBetweenDates(id, startDate, endDate, pageable);

    }

    public Page<Invoice> getByClientUnpaid(long id, Pageable pageable) {
        return invoiceRepository.findByResidence_ClientIdAndIsPaidFalse(id, pageable);
    }

    public Page<Invoice> findUnpaidInvoicesByClientAndResidence(long clientId, long residenceId, Pageable pageable) {
        return invoiceRepository.findUnpaidInvoicesByClientAndResidence(clientId,residenceId, pageable);
    }
}
