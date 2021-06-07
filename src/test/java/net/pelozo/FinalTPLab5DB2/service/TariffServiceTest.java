package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.controller.ClientController;
import net.pelozo.FinalTPLab5DB2.exception.ClientNotExistsException;
import net.pelozo.FinalTPLab5DB2.exception.NonExistentResourceException;
import net.pelozo.FinalTPLab5DB2.model.Client;
import net.pelozo.FinalTPLab5DB2.model.Tariff;
import net.pelozo.FinalTPLab5DB2.repository.TariffRepository;
import net.pelozo.FinalTPLab5DB2.utils.EntityURLBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Optional;

import static net.pelozo.FinalTPLab5DB2.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class TariffServiceTest {

    TariffRepository tariffRepository;
    TariffService tariffService;

    @BeforeEach
    public void setUp(){
        tariffRepository = mock(TariffRepository.class);
        tariffService = new TariffService(tariffRepository);
    }

    @Test
    public void getAllOkTest(){
        //given
        when(tariffRepository.findAll(aPageable())).thenReturn(aTariffPage());

        //when
        Page<Tariff> response = tariffService.getAll(aPageable());

        //then
        assertEquals(
                1,
                response.getTotalElements()
        );
        assertEquals(
                aTariff().getName(),
                response.getContent().get(0).getName()
        );
    }

    @Test
    public void getByIdOk() throws NonExistentResourceException {
        when(tariffRepository.findById(anyLong())).thenReturn(Optional.of(aTariff()));
        Tariff response = tariffService.getById(1L);
        assertEquals(
                aTariff().getName(),
                response.getName()
        );
    }

    @Test
    public void addTariffOkTest(){
        //given
        when(tariffRepository.save(aTariff())).thenReturn(aTariff());

        //when
        Tariff response = tariffService.add(aTariff());

        assertEquals(
                aTariff().getValue(),
                response.getValue()
        );
    }

    @Test
    public void getById_ThrowsNonExistentException() {
        when(tariffRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NonExistentResourceException.class, () -> {
            tariffService.getById(1L);
        });
    }

    @Test
    public void deleteTariffOk() throws NonExistentResourceException {
        //given
        when(tariffRepository.findById(anyLong())).thenReturn(Optional.of(aTariff()));
        //when
        tariffService.deleteById(1L);
        //then
        verify(tariffRepository, times(1)).findById(1L);
    }

    @Test
    public void deleteTariff_ThrowsNonExistentException(){
        //given
        when(tariffRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NonExistentResourceException.class, () -> {
            tariffService.deleteById(1L);
        });

    }
}
