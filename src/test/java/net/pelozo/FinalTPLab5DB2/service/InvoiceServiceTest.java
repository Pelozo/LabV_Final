package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.model.Invoice;
import net.pelozo.FinalTPLab5DB2.repository.InvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static net.pelozo.FinalTPLab5DB2.utils.TestUtils.anInvoice;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InvoiceServiceTest {

    InvoiceRepository invoiceRepository;
    InvoiceService invoiceService;

    @BeforeEach
    public void setUp(){
        invoiceRepository = mock(InvoiceRepository.class);
        invoiceService = new InvoiceService(invoiceRepository);
    }

    @Test
    public void addTestOk(){
        Invoice invoice = anInvoice();
        when(invoiceRepository.save(invoice)).thenReturn(invoice);

        Invoice invoice1 = invoiceService.add(invoice);

        assertEquals(invoice.getId(),invoice1.getId());
    }
}
