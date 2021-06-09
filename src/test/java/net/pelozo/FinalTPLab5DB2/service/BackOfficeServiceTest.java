package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.exception.InvalidCombinationUserPassword;
import net.pelozo.FinalTPLab5DB2.model.Backoffice;
import net.pelozo.FinalTPLab5DB2.model.Invoice;
import net.pelozo.FinalTPLab5DB2.model.dto.UserDto;
import net.pelozo.FinalTPLab5DB2.repository.BackofficeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static net.pelozo.FinalTPLab5DB2.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BackOfficeServiceTest {

    BackofficeRepository backofficeRepository;
    InvoiceService invoiceService;
    ModelMapper modelMapper;
    BackofficeService backofficeService;

    @BeforeEach
    public void setUp(){
        backofficeRepository = mock(BackofficeRepository.class);
        invoiceService = mock(InvoiceService.class);
        modelMapper = mock(ModelMapper.class);
        backofficeService = new BackofficeService(backofficeRepository,invoiceService,modelMapper);

    }

    @Test
    public void loginTestOk() throws InvalidCombinationUserPassword {
        when(backofficeRepository.findByUsernameAndPassword(any(String.class),any(String.class)))
                .thenReturn(aBackOffice());

        when(modelMapper.map(any(Backoffice.class), eq(UserDto.class)))
                .thenReturn(aUserDtoFromBackOffice());

        UserDto user = backofficeService.login("admin","admin");

        assertNotNull(user);
        assertEquals(aBackOffice().getUsername(),user.getUsername());
    }

    @Test
    public void loginTestThrowsInvalidCombinationUserPassword(){
        when(backofficeRepository.findByUsernameAndPassword(any(String.class),any(String.class)))
                .thenReturn(null);

        assertThrows(InvalidCombinationUserPassword.class,()->{
            backofficeService.login("admin","admin");
        });
    }

    @Test
    public void getUnpaidInvoicesByClientAndResidenceTestOk(){
        when(invoiceService.findUnpaidInvoicesByClientAndResidence(anyLong(),anyLong(),any(Pageable.class)))
                .thenReturn(anInvoicePage());

        Page<Invoice> invoices = backofficeService.getUnpaidInvoicesByClientAndResidence(1L,1L, aPageable());

        assertNotNull(invoices);
        assertEquals(anInvoice().getId(),invoices.getContent().get(0).getId());
    }

}
