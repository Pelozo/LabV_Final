package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.exception.ClientNotExistsException;
import net.pelozo.FinalTPLab5DB2.exception.InvalidCombinationUserPassword;
import net.pelozo.FinalTPLab5DB2.exception.InvalidIdException;
import net.pelozo.FinalTPLab5DB2.exception.ResidenceNotExistsException;
import net.pelozo.FinalTPLab5DB2.model.Backoffice;
import net.pelozo.FinalTPLab5DB2.model.Client;
import net.pelozo.FinalTPLab5DB2.model.Invoice;
import net.pelozo.FinalTPLab5DB2.model.Tariff;
import net.pelozo.FinalTPLab5DB2.model.dto.InvoiceDto;
import net.pelozo.FinalTPLab5DB2.model.dto.UserDto;
import net.pelozo.FinalTPLab5DB2.repository.BackofficeRepository;
import net.pelozo.FinalTPLab5DB2.repository.ClientRepository;
import net.pelozo.FinalTPLab5DB2.repository.InvoiceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BackofficeService {

    BackofficeRepository backofficeRepository;
    InvoiceService invoiceService;
    ModelMapper modelMapper;

    @Autowired
    public BackofficeService(BackofficeRepository backofficeRepository, InvoiceService invoiceService, ModelMapper modelMapper) {
        this.backofficeRepository = backofficeRepository;
        this.invoiceService = invoiceService;
        this.modelMapper = modelMapper;
    }


    public UserDto login(String username, String password) throws InvalidCombinationUserPassword {
        Backoffice b = backofficeRepository.findByUsernameAndPassword(username, password);

        if(b != null){
            return modelMapper.map(b, UserDto.class);
        }else {
            throw new InvalidCombinationUserPassword();
        }
    }

    public Page<InvoiceDto> getUnpaidInvoicesByClientAndResidence(long clientId, long residenceId, Pageable pageable) throws InvalidIdException, ClientNotExistsException, ResidenceNotExistsException {
        return invoiceService.findUnpaidInvoicesByClientAndResidence(clientId, residenceId, pageable);
    }
}
