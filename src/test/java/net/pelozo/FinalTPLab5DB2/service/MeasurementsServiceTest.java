package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.exception.MeterNotExistsException;
import net.pelozo.FinalTPLab5DB2.exception.ResidenceNotExistsException;
import net.pelozo.FinalTPLab5DB2.model.Measurement;
import net.pelozo.FinalTPLab5DB2.model.Meter;
import net.pelozo.FinalTPLab5DB2.model.Residence;
import net.pelozo.FinalTPLab5DB2.model.dto.MeasurementDto;
import net.pelozo.FinalTPLab5DB2.repository.MeasurementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static net.pelozo.FinalTPLab5DB2.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MeasurementsServiceTest {

     MeasurementRepository measurementRepository;
     MeterService meterService;
     ResidenceService residenceService;
     MeasurementService measurementService;


     @BeforeEach
    public void setUp(){
        measurementRepository = mock(MeasurementRepository.class);
        meterService = mock(MeterService.class);
        residenceService = mock(ResidenceService.class);
        measurementService = new MeasurementService(measurementRepository,meterService,residenceService);
    }

    @Test
    public void addTestOk() throws MeterNotExistsException, ResidenceNotExistsException {
        Optional<Meter>optMeter = Optional.of(aMeter());
        Optional<Residence>optResidence = Optional.of(aResidence());
        MeasurementDto measurementDto = aMeasurementDto();


        when(meterService
                .getBySerialNumberAndPassword(aMeter().getSerialNumber(),
                        aMeter().getPassword()))
                .thenReturn(optMeter);

        when(residenceService.getByMeter(aMeter()))
                .thenReturn(optResidence);

        //aca testeo los metodos dentro de los if statements
        assertTrue(optMeter.isPresent());
        assertTrue(optResidence.isPresent());

//        when(optResidence.get()).thenReturn(aResidence());
//        when(aMeasurement().getDate()).thenReturn(LocalDateTime.now());
//        when(aMeasurement().getKwhValue()).thenReturn(120.5f);

        when(measurementRepository.save(aMeasurement())).thenReturn(aMeasurement());

        Measurement measurement = measurementService.add(measurementDto);

        assertEquals(aMeasurement().getId(),measurement.getId());

    }
}
