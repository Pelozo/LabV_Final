package net.pelozo.FinalTPLab5DB2.controller;

import net.pelozo.FinalTPLab5DB2.model.Client;
import net.pelozo.FinalTPLab5DB2.model.PaginationResponse;
import net.pelozo.FinalTPLab5DB2.utils.AbstractController;
import net.pelozo.FinalTPLab5DB2.service.ClientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ClientController.class)
public class ClientControllerTest extends AbstractController{

    @MockBean
    private ClientService clientService;

    @Test
    public void getAllTestOk() throws Exception {
        //given
        Pageable pageable = PageRequest.of(0,1);


        //when
        // necesito simular que devuelvo una pagina
        //Mockito.when(clientService.getAll(pageable)).thenReturn(PaginationResponse<Client>)


        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .get("/client")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then

        assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
    }

}
