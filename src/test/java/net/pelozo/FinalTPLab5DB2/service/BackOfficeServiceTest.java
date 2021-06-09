package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.repository.BackofficeRepository;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.mock;

public class BackOfficeServiceTest {

    BackofficeRepository backofficeRepository;
    InvoiceService invoiceService;
    ModelMapper modelMapper;
    BackofficeService backofficeService;

    public void setUp(){
        backofficeRepository = mock(BackofficeRepository.class);
    }

}
