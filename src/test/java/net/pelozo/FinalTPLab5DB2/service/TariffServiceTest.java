package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.exception.IdViolationException;
import net.pelozo.FinalTPLab5DB2.exception.NonExistentResourceException;
import net.pelozo.FinalTPLab5DB2.model.Tariff;
import net.pelozo.FinalTPLab5DB2.repository.TariffRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import java.util.Optional;

import static net.pelozo.FinalTPLab5DB2.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
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
                aTariffPage().getTotalElements(),
                response.getTotalElements()
        );
        assertEquals(
                aTariffPage().getContent().get(0).getName(),
                response.getContent().get(0).getName()
        );
    }

    @Test
    public void getByIdOkTest(){
        when(tariffRepository.findById(anyLong())).thenReturn(Optional.of(aTariff()));

        try {
            Tariff response = tariffService.getById(1L);
            assertEquals(
                    aTariff().getName(),
                    response.getName()
            );
        } catch (NonExistentResourceException e) {
            fail();
        }

    }

    @Test
    public void getById_ThrowsNonExistentExceptionTest() {
        when(tariffRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NonExistentResourceException.class, () -> {
            tariffService.getById(1L);
        });
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
    public void deleteTariffOkTest(){
        //given
        when(tariffRepository.findById(anyLong())).thenReturn(Optional.of(aTariff()));

        try {
            //when
            tariffService.deleteById(1L);
            //then
            verify(tariffRepository, times(1)).findById(1L);
        } catch (NonExistentResourceException e) {
            fail();
        }

    }

    @Test
    public void deleteTariff_ThrowsNonExistentExceptionTest(){
        //given
        when(tariffRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NonExistentResourceException.class, () -> {
            tariffService.deleteById(1L);
        });
    }

    @Test
    public void updateTariffOkTest(){
        when(tariffRepository.findById(anyLong())).thenReturn(Optional.of(aTariff()));
        Tariff newTariff = aTariff();
        newTariff.setName("new name");

        try {
            tariffService.update(aTariff().getId(), newTariff);
            verify(tariffRepository, times(1)).findById(aTariff().getId());
            verify(tariffRepository, times(1)).save(newTariff);
        } catch (NonExistentResourceException | IdViolationException e) {
            fail();
        }
    }

    @Test
    public void updateTariffTestOkWithNull()  {
        when(tariffRepository.findById(anyLong())).thenReturn(Optional.of(aTariff()));

        Tariff wrongTariff = aTariff();
        wrongTariff.setId(null);
        try {
            tariffService.update(aTariff().getId(), wrongTariff);
        } catch (NonExistentResourceException | IdViolationException e) {
            fail();
        }

        verify(tariffRepository, times(1)).save(aTariff());
    }

    @Test
    public void updateTariff_ThrowsNonExistentExceptionTest(){
        when(tariffRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NonExistentResourceException.class, () -> {
            tariffService.update(aTariff().getId(), aTariff());
        });
    }

    @Test
    public void updateTariff_ThrowsIdViolationExceptionTest(){
        when(tariffRepository.findById(anyLong())).thenReturn(Optional.of(aTariff()));

        Tariff wrongTariff = aTariff();
        wrongTariff.setId(8L);

        assertThrows(IdViolationException.class, () -> {
            tariffService.update(aTariff().getId(), wrongTariff);
        });
    }




}
