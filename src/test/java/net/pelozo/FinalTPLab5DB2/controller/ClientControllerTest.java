package net.pelozo.FinalTPLab5DB2.controller;

import net.pelozo.FinalTPLab5DB2.dto.ClientDto;
import net.pelozo.FinalTPLab5DB2.service.InvoiceService;
import net.pelozo.FinalTPLab5DB2.service.MeasurementService;
import net.pelozo.FinalTPLab5DB2.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static net.pelozo.FinalTPLab5DB2.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;


public class ClientControllerTest{

    private ClientService clientService;

    private InvoiceService invoiceService;

    private MeasurementService measurementService;

    private ModelMapper modelMapper;

    @Mock
    private ClientController clientController;

    @BeforeEach
    public void setUp(){
        clientService = mock(ClientService.class);
        invoiceService = mock(InvoiceService.class);
        measurementService = mock(MeasurementService.class);
        modelMapper = mock(ModelMapper.class);
        clientController = new ClientController(clientService,invoiceService,modelMapper,measurementService);
    }

    @Test
    public void getAllTestOk() throws Exception {
        //when
        Pageable pageable = PageRequest.of(1,10);
        Mockito.when(clientService.getAll(pageable)).thenReturn(aClientPage());

        ResponseEntity<List<ClientDto>> response = clientController.getAll(pageable);

        //then
        assertEquals(HttpStatus.OK.value(),response.getStatusCodeValue());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(aClientPage().getContent().get(0).getDni(),response.getBody().get(0).getDni());
    }

//    @Test
//    public void getAllTestOk() throws Exception {
//
//
//        Mockito.when(clientService.getAll(Mockito.any(Pageable.class)))
//                .thenReturn(aClientPage());
//
//        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
//                .get("/clients")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
//    }

//    @Test
//    public void addClientTestOk() throws Exception {
//
//        Mockito.when(clientService.add(Mockito.any(Client.class)))
//                .thenReturn(aClient());
//
//        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
//                .post("/clients")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(aClientJSON()))
//                .andExpect(status().isCreated());
//
//        assertEquals(HttpStatus.CREATED.value(), resultActions.andReturn().getResponse().getStatus());
//    }
//
//    @Test
//    public void getByIdTestOk() throws Exception, ClientNotExistsException {
//
//        Mockito.when(clientService.getById(Mockito.any(Long.class)))
//                .thenReturn(aClient());
//
//        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
//                    .get("/clients/1")
//                    .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk());
//
//        assertEquals(HttpStatus.OK.value(),resultActions.andReturn().getResponse().getStatus());
//    }

}
