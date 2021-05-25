package net.pelozo.FinalTPLab5DB2.controller;

import net.pelozo.FinalTPLab5DB2.exception.ClientNotExistsException;
import net.pelozo.FinalTPLab5DB2.model.Client;
import net.pelozo.FinalTPLab5DB2.utils.AbstractController;
import net.pelozo.FinalTPLab5DB2.service.ClientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static net.pelozo.FinalTPLab5DB2.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ClientController.class)
public class ClientControllerTest extends AbstractController{

    @MockBean
    private ClientService clientService;

    @Test
    public void getAllTestOk() throws Exception {


        Mockito.when(clientService.getAll(Mockito.any(Pageable.class)))
                .thenReturn(aClientPage());

        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .get("/clients")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
    }

    @Test
    public void addClientTestOk() throws Exception {

        Mockito.when(clientService.add(Mockito.any(Client.class)))
                .thenReturn(aClient());

        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aClientJSON()))
                .andExpect(status().isCreated());

        assertEquals(HttpStatus.CREATED.value(), resultActions.andReturn().getResponse().getStatus());
    }

    @Test
    public void getByIdTestOk() throws Exception, ClientNotExistsException {

        Mockito.when(clientService.getById(Mockito.any(Long.class)))
                .thenReturn(aClient());

        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                    .get("/clients/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

        assertEquals(HttpStatus.OK.value(),resultActions.andReturn().getResponse().getStatus());
    }

}
