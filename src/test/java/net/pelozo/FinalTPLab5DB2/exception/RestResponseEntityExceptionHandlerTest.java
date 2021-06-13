package net.pelozo.FinalTPLab5DB2.exception;

import net.pelozo.FinalTPLab5DB2.controller.TariffController;
import net.pelozo.FinalTPLab5DB2.model.Tariff;
import net.pelozo.FinalTPLab5DB2.service.TariffService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Locale;

import static net.pelozo.FinalTPLab5DB2.utils.TestUtils.aTariffPage;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

public class RestResponseEntityExceptionHandlerTest{

    private MockMvc mockMvc;



    private TariffService tariffService;
    private TariffController tariffController;

    @BeforeEach
    public void setUp() {

        tariffService = mock(TariffService.class);
        tariffController = mock(TariffController.class);

        mockMvc = MockMvcBuilders.standaloneSetup(tariffController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    public void getTariffById_ThrowsNonExistentResourceExceptionTest() throws NonExistentResourceException {
        given(tariffController.getById(anyLong())).willAnswer( invocation -> { throw new NonExistentResourceException(); });
        try {
            mockMvc.perform(get("/tariff/100"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
           fail();
        }
    }

    @Test
    public void updateTariff_ThrowsNonExistentResourceExceptionTest() throws IdViolationException, NonExistentResourceException {
        given(tariffController.updateTariff(anyLong(), any(Tariff.class))).willAnswer(invocation -> { throw new IdViolationException(); });
        try {
            mockMvc.perform(put("/tariff/1"))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
           fail();
        }
    }






}
