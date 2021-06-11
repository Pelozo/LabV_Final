package net.pelozo.FinalTPLab5DB2.controller;

import net.pelozo.FinalTPLab5DB2.exception.ClientNotExistsException;
import net.pelozo.FinalTPLab5DB2.exception.IdViolationException;
import net.pelozo.FinalTPLab5DB2.exception.NonExistentResourceException;
import net.pelozo.FinalTPLab5DB2.model.Backoffice;
import net.pelozo.FinalTPLab5DB2.model.Client;
import net.pelozo.FinalTPLab5DB2.model.Tariff;
import net.pelozo.FinalTPLab5DB2.model.dto.ClientDto;
import net.pelozo.FinalTPLab5DB2.service.TariffService;
import net.pelozo.FinalTPLab5DB2.utils.EntityURLBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import static net.pelozo.FinalTPLab5DB2.utils.TestUtils.*;
import static net.pelozo.FinalTPLab5DB2.utils.TestUtils.aClient;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
        //given
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(tariffService.add(aTariff())).thenReturn(aTariff());

        //when
        ResponseEntity response = tariffController.addTariff(aTariff());

        //then
        assertEquals(
                HttpStatus.CREATED.value(),
                response.getStatusCodeValue());

        assertEquals(
                EntityURLBuilder.buildURL("tariffs", aTariff().getId()).toString(),
                response.getHeaders().get("Location").get(0)
        );
    }

    @Test
    public void getByIdOkTest(){
        //given
        try {
            when(tariffService.getById(anyLong())).thenReturn(aTariff());

            //when
            ResponseEntity<Tariff> response = tariffController.getById(aTariff().getId());

            //then
            assertEquals(
                    HttpStatus.OK,
                    response.getStatusCode()
            );

            assertEquals(
                    aTariff().getName(),
                    response.getBody().getName()
            );
        } catch (NonExistentResourceException e) {
            fail();
        }
    }

    @Test
    public void getById_ThrowsNonExistentResourceExceptionTest() throws NonExistentResourceException {
        //given
        when(tariffService.getById(anyLong())).thenThrow(new NonExistentResourceException());
        //then
        assertThrows(NonExistentResourceException.class, () -> {
            tariffService.getById(1L);
        });
    }

    @Test
    public void deleteTariffOk(){
        //given
        try {
            doNothing().when(tariffService).deleteById(anyLong());
            //when
            ResponseEntity responseEntity = tariffController.deleteById(1L);

            //then
            assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
            verify(tariffService, times(1)).deleteById(aTariff().getId());
        } catch (NonExistentResourceException e) {
            fail();
        }


    }

    @Test
    public void deleteTariff_ThrowsNonExistentResourceExceptionTest() throws NonExistentResourceException {
        //given
        when(tariffController.deleteById(anyLong())).thenThrow(new NonExistentResourceException());
        //then
        assertThrows(NonExistentResourceException.class, () -> {
            tariffController.deleteById(20L);
        });
    }

    @Test
    public void updateTariffOkTest(){
        try {
            //when
            ResponseEntity response  = tariffController.updateTariff(aTariff().getId(), aTariff());
            //then
            assertEquals(
                    HttpStatus.ACCEPTED.value(),
                    response.getStatusCodeValue());

            verify(tariffService, times(1)).update(aTariff().getId(), aTariff());

        } catch (NonExistentResourceException | IdViolationException e) {
            fail();
        }

    }
}
