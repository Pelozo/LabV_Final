package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.model.Invoice;
import net.pelozo.FinalTPLab5DB2.model.dto.InvoiceDto;
import net.pelozo.FinalTPLab5DB2.repository.InvoiceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class InvoiceService {


    private final InvoiceRepository invoiceRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, ModelMapper modelMapper) {
        this.invoiceRepository = invoiceRepository;
        this.modelMapper = modelMapper;
    }

    public Page<Invoice> getAll(Pageable pageable) {
        return invoiceRepository.findAll(pageable);
    }

    public Page<InvoiceDto> getByClientId(long id, Pageable pageable) {
        return invoiceRepository.findByResidence_Client_Id(id, pageable).map(o->modelMapper.map(o, InvoiceDto.class));

    }

    public Page<InvoiceDto> getByClientIdAndDate(long id, Date startDate, Date endDate, Pageable pageable) {

        return invoiceRepository.findByClientBetweenDates(id, startDate, endDate, pageable).map(o->modelMapper.map(o, InvoiceDto.class));

    }

    public Page<InvoiceDto> getByClientUnpaid(long id, Pageable pageable) {
        return invoiceRepository.findByResidence_ClientIdAndIsPaidFalse(id, pageable).map(o->modelMapper.map(o, InvoiceDto.class));
    }

    public Page<InvoiceDto> findUnpaidInvoicesByClientAndResidence(long clientId, long residenceId, Pageable pageable) {
        return invoiceRepository.findUnpaidInvoicesByClientAndResidence(clientId,residenceId, pageable).map(o->modelMapper.map(o, InvoiceDto.class));
    }
}
