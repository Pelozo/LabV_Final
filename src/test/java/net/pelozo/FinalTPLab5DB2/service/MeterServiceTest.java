package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.model.Meter;
import net.pelozo.FinalTPLab5DB2.repository.MeterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static net.pelozo.FinalTPLab5DB2.utils.TestUtils.aMeter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    public void getBySnumberAndPasswordTestOk(){
           when(meterRepository
                   .findBySerialNumberAndPassword(Mockito.any(String.class),Mockito.any(String.class)))
                   .thenReturn(Optional.of(aMeter()));

           Optional<Meter> meter = meterService.getBySerialNumberAndPassword("123asd","asdf1234");

           assertEquals(aMeter().getId(),meter.get().getId());
    }

    @Test
    public void getBySnumberAndPasswordTestEmpty(){
        when(meterRepository
                .findBySerialNumberAndPassword(Mockito.any(String.class),Mockito.any(String.class)))
                .thenReturn(Optional.empty());

        Optional<Meter> meter = meterService.getBySerialNumberAndPassword("123asd","asdf1234");

        assertEquals(Optional.empty(),meter);
    }
}
