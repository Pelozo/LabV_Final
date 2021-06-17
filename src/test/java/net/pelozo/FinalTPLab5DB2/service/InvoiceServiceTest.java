package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.exception.ClientNotExistsException;
import net.pelozo.FinalTPLab5DB2.exception.InvalidIdException;
import net.pelozo.FinalTPLab5DB2.exception.ResidenceNotExistsException;
import net.pelozo.FinalTPLab5DB2.model.Invoice;
import net.pelozo.FinalTPLab5DB2.model.Residence;
import net.pelozo.FinalTPLab5DB2.model.dto.InvoiceDto;
import net.pelozo.FinalTPLab5DB2.repository.ClientRepository;
import net.pelozo.FinalTPLab5DB2.repository.InvoiceRepository;
import net.pelozo.FinalTPLab5DB2.repository.ResidenceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Optional;

import static net.pelozo.FinalTPLab5DB2.utils.TestUtils.*;
import static org.assertj.core.util.DateUtil.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InvoiceServiceTest {

    InvoiceRepository invoiceRepository;
    InvoiceService invoiceService;
    ClientRepository clientRepository;
    ResidenceRepository residenceRepository;
    ModelMapper modelMapper;

    @BeforeEach
    public void setUp(){
        modelMapper = mock(ModelMapper.class);
        invoiceRepository = mock(InvoiceRepository.class);
        clientRepository = mock(ClientRepository.class);
        residenceRepository = mock(ResidenceRepository.class);
        invoiceService = new InvoiceService(invoiceRepository, modelMapper, clientRepository, residenceRepository);
        when(modelMapper.map(any(Invoice.class), eq(InvoiceDto.class))).thenReturn(anInvoiceDto());
    }


    @Test
    public void getAllOkTest(){
        when(invoiceRepository.findAll(any(Pageable.class))).thenReturn(anInvoicePage());

        Page<Invoice> response = invoiceService.getAll(aPageable());

        assertEquals(anInvoicePage().getTotalElements(), response.getTotalElements());
        assertEquals(anInvoicePage().getContent().get(0).getLastReading(), response.getContent().get(0).getLastReading());
    }


    @Test
    public void getByClientIdOkTest(){
        when(invoiceRepository.findByResidence_Client_Id(anyLong(), any(Pageable.class))).thenReturn(anInvoicePage());

        Page<InvoiceDto> response = invoiceService.getByClientId(1,aPageable());

        assertEquals(anInvoicePage().getTotalElements(), response.getTotalElements());
        assertEquals(aInvoiceDtoPage().getContent().get(0).getTotalAmount(), response.getContent().get(0).getTotalAmount());


    }

    @Test
    public void getByClientIdAndDateOkTest(){
        when(invoiceRepository.findByClientBetweenDates(anyLong(), any(Date.class), any(Date.class), any(Pageable.class))).thenReturn(anInvoicePage());

        Page<InvoiceDto> response = invoiceService.getByClientIdAndDate(1, now(), now(), aPageable());

        assertEquals(anInvoicePage().getTotalElements(), response.getTotalElements());
        assertEquals(aInvoiceDtoPage().getContent().get(0).getTotalAmount(), response.getContent().get(0).getTotalAmount());
    }

    @Test
    public void getByClientUnpaidOkTest(){
        when(invoiceRepository.findByResidence_ClientIdAndIsPaidFalse(anyLong(), any(Pageable.class))).thenReturn(anInvoicePage());

        Page<InvoiceDto> response = invoiceService.getByClientUnpaid(1,aPageable());

        assertEquals(anInvoicePage().getTotalElements(), response.getTotalElements());
        assertEquals(aInvoiceDtoPage().getContent().get(0).getTotalAmount(), response.getContent().get(0).getTotalAmount());
    }


}
