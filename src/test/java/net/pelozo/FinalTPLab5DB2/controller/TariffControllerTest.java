package net.pelozo.FinalTPLab5DB2.controller;

import net.pelozo.FinalTPLab5DB2.model.Tariff;
import net.pelozo.FinalTPLab5DB2.service.TariffService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static net.pelozo.FinalTPLab5DB2.utils.TestUtils.aPageable;
import static net.pelozo.FinalTPLab5DB2.utils.TestUtils.aTariffPage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TariffControllerTest {

    TariffService tariffService;

    TariffController tariffController;

    @BeforeEach
    public void setUp(){
        tariffService = mock(TariffService.class);
        tariffController = new TariffController(tariffService);
    }

    @Test
    public void getAllTestOk(){
        when(tariffService.getAll(any(Pageable.class))).thenReturn(aTariffPage());

        ResponseEntity<List<Tariff>> response = tariffController.getAll(aPageable());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(aTariffPage().getTotalElements(), Long.valueOf(response.getHeaders().get("X-Total-Pages").get(0)));
        assertEquals(aTariffPage().getContent().get(0).getName(), response.getBody().get(0).getName());
    }

    @Test
    public void getAllTestEmpty(){
        when(tariffService.getAll(any(Pageable.class))).thenReturn(Page.empty(aPageable()));

        ResponseEntity<List<Tariff>> response = tariffController.getAll(aPageable());

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(0, Long.valueOf(response.getHeaders().get("X-Total-Pages").get(0)));
    }

    @Test
    public void addTariffOkTest(){

    }
}
