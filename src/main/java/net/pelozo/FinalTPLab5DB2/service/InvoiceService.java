package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.exception.ClientNotExistsException;
import net.pelozo.FinalTPLab5DB2.exception.InvalidIdException;
import net.pelozo.FinalTPLab5DB2.exception.ResidenceNotExistsException;
import net.pelozo.FinalTPLab5DB2.model.Client;
import net.pelozo.FinalTPLab5DB2.model.Invoice;
import net.pelozo.FinalTPLab5DB2.model.Residence;
import net.pelozo.FinalTPLab5DB2.model.dto.InvoiceDto;
import net.pelozo.FinalTPLab5DB2.repository.ClientRepository;
import net.pelozo.FinalTPLab5DB2.repository.InvoiceRepository;
import net.pelozo.FinalTPLab5DB2.repository.ResidenceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class InvoiceService {


    private final InvoiceRepository invoiceRepository;
    private final ModelMapper modelMapper;
    private final ClientRepository clientRepository;
    private final ResidenceRepository residenceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, ModelMapper modelMapper, ClientRepository clientRepository, ResidenceRepository residenceRepository) {
        this.invoiceRepository = invoiceRepository;
        this.modelMapper = modelMapper;
        this.clientRepository = clientRepository;
        this.residenceRepository = residenceRepository;
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

    public Page<InvoiceDto> findUnpaidInvoicesByClientAndResidence(long clientId, long residenceId, Pageable pageable) throws ResidenceNotExistsException, ClientNotExistsException, InvalidIdException {

        Optional<Client> client = clientRepository.findById(clientId);
        Optional<Residence> residence = residenceRepository.findById(residenceId);

        if(client.isEmpty()){
            throw new ClientNotExistsException();
        }
        if(residence.isEmpty()){
            throw new ResidenceNotExistsException();
        }
        if(!client.get().getResidences().contains(residence.get())){
            throw new InvalidIdException("Residence");
        }

        return invoiceRepository.findUnpaidInvoicesByClientAndResidence(clientId,residenceId, pageable).map(o->modelMapper.map(o, InvoiceDto.class));
    }
}
