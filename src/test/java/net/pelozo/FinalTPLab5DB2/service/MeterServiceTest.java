package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.exception.IdViolationException;
import net.pelozo.FinalTPLab5DB2.exception.NonExistentResourceException;
import net.pelozo.FinalTPLab5DB2.model.Meter;
import net.pelozo.FinalTPLab5DB2.model.Tariff;
import net.pelozo.FinalTPLab5DB2.model.dto.MeterDto;
import net.pelozo.FinalTPLab5DB2.repository.MeterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static net.pelozo.FinalTPLab5DB2.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MeterServiceTest {

    MeterRepository meterRepository;
    MeterService meterService;
    ModelMapper modelMapper;

    @BeforeEach
    public void setUp(){
        modelMapper = mock(ModelMapper.class);
        meterRepository = mock(MeterRepository.class);
        meterService = new MeterService(meterRepository, modelMapper);
    }

    @Test
    public void getBySerialnumberAndPasswordTestOk(){
           when(meterRepository
                   .findBySerialNumberAndPassword(Mockito.any(String.class),Mockito.any(String.class)))
                   .thenReturn(Optional.of(aMeter()));

           Optional<Meter> meter = meterService.getBySerialNumberAndPassword("123asd","asdf1234");

           assertEquals(aMeter().getId(),meter.get().getId());
    }

    @Test
    public void getBySerialnumberAndPasswordTestEmpty(){
        when(meterRepository
                .findBySerialNumberAndPassword(Mockito.any(String.class),Mockito.any(String.class)))
                .thenReturn(Optional.empty());

        Optional<Meter> meter = meterService.getBySerialNumberAndPassword("123asd","asdf1234");

        verify(meterRepository, times(1)).findBySerialNumberAndPassword(any(), any());
        assertEquals(Optional.empty(),meter);
    }

    @Test
    public void addMeterOkTest(){
        when(meterRepository.save(any(Meter.class))).thenReturn(aMeter());
        when(modelMapper.map(any(Meter.class), eq(MeterDto.class))).thenReturn(aMeterDto());

        MeterDto response = meterService.add(aMeter());

        verify(meterRepository, times(1)).save(any());
        assertEquals(aMeterDto(), response);
    }


    @Test
    public void deleteByIdOkTest(){
        when(meterRepository.findById(anyLong())).thenReturn(Optional.of(aMeter()));

        try {
            meterService.deleteById(1L);

            verify(meterRepository, times(1)).findById(1L);
        } catch (NonExistentResourceException e) {
            fail();
        }
    }


    @Test
    public void deleteById_ThrowsNonExistentResourceExceptionTest(){
        when(meterRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NonExistentResourceException.class, () -> {
            meterService.deleteById(1L);
        });
    }

    @Test
    public void getAllOkTest(){
        when(meterRepository.findAll(any(Pageable.class))).thenReturn(aMeterPage());
        when(modelMapper.map(any(Meter.class), eq(MeterDto.class))).thenReturn(aMeterDto());

        Page<MeterDto> response = meterService.getAll(aPageable());

        assertEquals(aMeterPage().getTotalElements(), response.getTotalElements());
        assertEquals(
                aMeterPage().getContent().get(0).getSerialNumber(),
                response.getContent().get(0).getSerialNumber()
        );
    }

    @Test
    public void getByIdOkTest(){
        when(meterRepository.findById(anyLong())).thenReturn(Optional.of(aMeter()));
        when(modelMapper.map(any(Meter.class), eq(MeterDto.class))).thenReturn(aMeterDto());

        try {
            MeterDto response = meterService.getById(1L);

            assertEquals(aMeter().getSerialNumber(),response.getSerialNumber());
        } catch (NonExistentResourceException e) {
            fail();
        }
    }

    @Test
    public void getById_ThrowsNonExistentResourceExceptionTest(){
        when(meterRepository.findById(anyLong())). thenReturn(Optional.empty());

        assertThrows(NonExistentResourceException.class, () -> {
            meterService.getById(1L);
        });
    }

    @Test
    public void updateMeterOkTest(){
        when(meterRepository.findById(anyLong())).thenReturn(Optional.of(aMeter()));
        when(meterRepository.findByModelId(anyLong())).thenReturn(Optional.of(aMeterModel()));
        Meter newMeter = aMeter();
        newMeter.setSerialNumber("A8B23");

        try {
            meterService.update(aMeter().getId(), newMeter);
            verify(meterRepository, times(1)).findById(aMeter().getId());
            verify(meterRepository, times(1)).save(newMeter);
        } catch (NonExistentResourceException | IdViolationException e) {
            fail();
        }
    }

    @Test
    public void updateMeterTestOkWithNull()  {
        when(meterRepository.findById(anyLong())).thenReturn(Optional.of(aMeter()));
        when(meterRepository.findByModelId(anyLong())).thenReturn(Optional.of(aMeterModel()));
        Meter newMeter = aMeter();
        newMeter.setId(null);

        try {
            meterService.update(aMeter().getId(), newMeter);
        } catch (NonExistentResourceException | IdViolationException e) {
            fail();
        }
        verify(meterRepository, times(1)).save(aMeter());
    }

    @Test
    public void updateMeter_ThrowsNonExistentExceptionTest(){
        when(meterRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(meterRepository.findByModelId(anyLong())).thenReturn(Optional.of(aMeterModel()));
        assertThrows(NonExistentResourceException.class, () -> {
            meterService.update(aMeter().getId(), aMeter());
        });

    }

    @Test
    public void updateMeter_ThrowsIdViolationExceptionTest(){
        when(meterRepository.findById(anyLong())).thenReturn(Optional.of(aMeter()));
        when(meterRepository.findByModelId(anyLong())).thenReturn(Optional.of(aMeterModel()));

        Meter newMeter = aMeter();
        newMeter.setId(8L);
        assertThrows(IdViolationException.class, () -> {
            meterService.update(aTariff().getId(), newMeter);
        });
    }

    @Test
    public void updateMeter_noModelMeter_ThrowsNonExistentExceptionTest(){
        when(meterRepository.findById(anyLong())).thenReturn(Optional.of(aMeter()));
        when(meterRepository.findByModelId(anyLong())).thenReturn(Optional.empty());
        assertThrows(NonExistentResourceException.class, () -> {
            meterService.update(aMeter().getId(), aMeter());
        });

    }

}
