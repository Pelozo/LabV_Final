package net.pelozo.FinalTPLab5DB2.controller;

import net.pelozo.FinalTPLab5DB2.utils.AbstractController;
import net.pelozo.FinalTPLab5DB2.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static net.pelozo.FinalTPLab5DB2.utils.TestUtils.aClientJSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ClientController.class)
public class ClientControllerTest extends AbstractController{

    @MockBean
    private ClientService clientService;

    @Test
    public void getAllTestOk() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .get("/clients")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
    }

    @Test
    public void addClientTestOk() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aClientJSON()))
                .andExpect(status().isOk());

        assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
    }

    @Test
    public void getByIdTestOk() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                    .get("/clients/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

        assertEquals(HttpStatus.OK.value(),resultActions.andReturn().getResponse().getStatus());
    }

}
