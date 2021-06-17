package net.pelozo.FinalTPLab5DB2.controller;

import net.pelozo.FinalTPLab5DB2.exception.NonExistentResourceException;
import net.pelozo.FinalTPLab5DB2.model.Intake;
import net.pelozo.FinalTPLab5DB2.model.Invoice;
import net.pelozo.FinalTPLab5DB2.model.dto.ClientDto;
import net.pelozo.FinalTPLab5DB2.exception.ClientNotExistsException;

import net.pelozo.FinalTPLab5DB2.model.Client;
import net.pelozo.FinalTPLab5DB2.model.dto.MeasurementsDto;
import net.pelozo.FinalTPLab5DB2.service.InvoiceService;
import net.pelozo.FinalTPLab5DB2.service.MeasurementService;
import net.pelozo.FinalTPLab5DB2.service.ClientService;
import net.pelozo.FinalTPLab5DB2.service.ResidenceService;
import net.pelozo.FinalTPLab5DB2.utils.EntityURLBuilder;
import net.pelozo.FinalTPLab5DB2.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.mock.web.MockHttpServletRequest;

import static net.pelozo.FinalTPLab5DB2.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientControllerTest{

    private ClientService clientService;
    private InvoiceService invoiceService;
    private MeasurementService measurementService;
    private ModelMapper modelMapper;
    private ResidenceService residenceService;

    private ClientController clientController;

    @BeforeEach
    public void setUp(){
        clientService = mock(ClientService.class);
        invoiceService = mock(InvoiceService.class);
        measurementService = mock(MeasurementService.class);
        modelMapper = mock(ModelMapper.class);
        residenceService = mock(ResidenceService.class);
        clientController = new ClientController(clientService,invoiceService,modelMapper,measurementService, residenceService);
    }

    @Test
    public void getAllTestOk(){
        //given
        when(clientService.getAll(aPageable())).thenReturn(aClientPage());
        when(modelMapper.map(any(), eq(ClientDto.class))).thenReturn(aClientDto());

        //when
        ResponseEntity<List<ClientDto>> response = clientController.getAll(aPageable());

        //then
        assertEquals(HttpStatus.OK.value(),response.getStatusCodeValue());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(
                aClientPage().getContent().get(0).getDni(),
                response.getBody().get(0).getDni()
        );
    }


    @Test
    public void addClientOkTest(){
        //given
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Client client = aClient();
        when(clientService.add(client)).thenReturn(client);

        //when
        ResponseEntity responseEntity = clientController.add(aClient());

        //then
        assertEquals(
                HttpStatus.CREATED.value(),
                responseEntity.getStatusCode().value());

        assertEquals(
                EntityURLBuilder.buildURL("clients", client.getId()).toString(),
                responseEntity.getHeaders().get("Location").get(0)
        );
    }

    @Test
    public void deleteClientOk(){

        try {
            //given
            doNothing().when(clientService).deleteById(anyLong());
            //when
            ResponseEntity<Object> responseEntity = clientController.deleteById(1L);

            //then
            assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        } catch (ClientNotExistsException e) {
           fail();
        }
    }

    @Test
    public void deleteClientDoesntExists() throws ClientNotExistsException {
        //given
        doThrow(new ClientNotExistsException()).when(clientService).deleteById(anyLong());
        //then
        assertThrows(ClientNotExistsException.class, () -> {
            clientController.deleteById(20L);
        });

    }

    @Test
    public void getByIdTestOk(){

        try {
            //given
            when(clientService.getById(anyLong())).thenReturn(aClient());
            when(modelMapper.map(any(), eq(ClientDto.class))).thenReturn(aClientDto());

            //when
            ResponseEntity<ClientDto> response = clientController.getById(1L);

            //then
            assertEquals(
                    HttpStatus.OK,
                    response.getStatusCode()
            );

            assertEquals(
                    aClient().getDni(),
                    response.getBody().getDni()
            );
        } catch (ClientNotExistsException e) {
            fail();
        }

    }

    @Test
    public void getByIdTestDoesntExists() throws ClientNotExistsException {
        //given
        doThrow(new ClientNotExistsException()).when(clientService).getById(anyLong());
        //then
        assertThrows(ClientNotExistsException.class, () -> {
            clientController.getById(1L);
        });
    }

    @Test
    public void getInvoicesByClientAndDatesTest(){
        //given
        Date date = mock(Date.class);
        when(date.getTime()).thenReturn(30L);
        when(invoiceService.getByClientIdAndDate(anyLong(), eq(date), eq(date), eq(aPageable()))).thenReturn(anInvoicePage());
        //when
        ResponseEntity<List<Invoice>> response = clientController.getInvoices(1L, date, date, aPageable());
        //then
        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );
        assertEquals(
                1,
                response.getBody().size()
        );
        assertEquals(
                anInvoicePage().toList().get(0).getLastReading(),
                response.getBody().get(0).getLastReading()
        );
    }

    @Test
    public void getUnpaidInvoicesByClient(){
        //given
        when(invoiceService.getByClientUnpaid(anyLong(), eq(aPageable()))).thenReturn(anInvoicePage());
        //when
        ResponseEntity<List<Invoice>> response = clientController.getUnpaidInvoices(1L, aPageable());

        //then
        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );
        assertEquals(
                1,
                response.getBody().size()
        );
        assertEquals(
                anInvoicePage().toList().get(0).getLastReading(),
                response.getBody().get(0).getLastReading()
        );
    }

    @Test
    public void getInvoicesByUserAndDate_ExpectsEmptyResponseTest(){
        //given
        Date date = mock(Date.class);
        when(date.getTime()).thenReturn(30L);
        when(invoiceService.getByClientIdAndDate(anyLong(), eq(date), eq(date), eq(aPageable()))).thenReturn(Page.empty());
        //when
        ResponseEntity<List<Invoice>> response = clientController.getInvoices(1L, date, date, aPageable());
        //then
        assertEquals(
                HttpStatus.NO_CONTENT,
                response.getStatusCode()
        );
        assertEquals(
                0,
                response.getBody().size()
        );
    }


    @Test
    public void getIntakeByDateRangeTestOk() throws NonExistentResourceException {
        when(measurementService.getIntakeByRangeOfDates(anyLong(),any(LocalDateTime.class),any(LocalDateTime.class)))
                .thenReturn(Optional.of(anIntake()));

        ResponseEntity<Optional<Intake>> response = clientController.getIntakeByDateRange(1,LocalDateTime.now(),LocalDateTime.now());


        assertNotNull(response.getBody());
        assertTrue(response.getBody().isPresent());
        assertNotNull(response.getBody().get());
        assertEquals(response.getBody().get().getKwhPrice(),anIntake().getKwhPrice());
        assertEquals(response.getStatusCode(),HttpStatus.OK);

    }

    @Test
    public void getIntakeByDateRangeTest_HttpNoContent() throws NonExistentResourceException {
        when(measurementService.getIntakeByRangeOfDates(anyLong(),any(LocalDateTime.class),any(LocalDateTime.class)))
                .thenReturn(Optional.empty());

        ResponseEntity<Optional<Intake>> response = clientController.getIntakeByDateRange(1,LocalDateTime.now(),LocalDateTime.now());


        assertNotNull(response.getBody());
        assertFalse(response.getBody().isPresent());
        assertEquals(response.getStatusCode(),HttpStatus.NO_CONTENT);

    }

    @Test
    public void getMeasurementsByDateRangeTestOk(){
        when(measurementService.getMeasurementsByDateRange(anyLong(),any(LocalDateTime.class),any(LocalDateTime.class),eq(aPageable())))
                .thenReturn(aMeasurementsDtoPage());

        ResponseEntity<List<MeasurementsDto>> response = clientController.getMeasurementsByDateRange(1,LocalDateTime.now(),LocalDateTime.now(),aPageable());

        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        response.getBody().forEach(Assertions::assertNotNull);
        assertEquals(response.getBody().get(0).getKwhPrice(),aMeasurementsDto().getKwhPrice());
        assertEquals(response.getStatusCode(),HttpStatus.OK);
    }


    @Test
    public void getTopTenConsumersTestOk(){
        when(clientService.getTopTenConsumers(any(LocalDate.class),any(LocalDate.class)))
                .thenReturn(List.of(aClientDto()));

        ResponseEntity<List<ClientDto>> response = clientController.getTopTenConsumers(LocalDate.now(),LocalDate.now());

        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        response.getBody().forEach(Assertions::assertNotNull);
        assertEquals(response.getBody().get(0).getId(),aClientDto().getId());
        assertEquals(response.getStatusCode(),HttpStatus.OK);

    }


}
