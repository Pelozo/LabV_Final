package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.exception.MeterNotExistsException;
import net.pelozo.FinalTPLab5DB2.exception.NonExistentResourceException;
import net.pelozo.FinalTPLab5DB2.exception.ResidenceNotExistsException;
import net.pelozo.FinalTPLab5DB2.model.Intake;
import net.pelozo.FinalTPLab5DB2.model.Measurement;
import net.pelozo.FinalTPLab5DB2.model.Meter;
import net.pelozo.FinalTPLab5DB2.model.Residence;
import net.pelozo.FinalTPLab5DB2.model.dto.MeasurementDto;
import net.pelozo.FinalTPLab5DB2.model.dto.MeasurementsDto;
import net.pelozo.FinalTPLab5DB2.repository.MeasurementRepository;
import net.pelozo.FinalTPLab5DB2.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static net.pelozo.FinalTPLab5DB2.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MeasurementsServiceTest {

     MeasurementRepository measurementRepository;
     MeterService meterService;
     ResidenceService residenceService;
     MeasurementService measurementService;
     ModelMapper modelMapper;


     @BeforeEach
    public void setUp(){
        measurementRepository = mock(MeasurementRepository.class);
        meterService = mock(MeterService.class);
        residenceService = mock(ResidenceService.class);
        modelMapper = mock(ModelMapper.class);
        measurementService = new MeasurementService(measurementRepository,meterService,residenceService, modelMapper);
    }

    @Test
    public void addTestOk(){
        Optional<Meter>optMeter = Optional.of(aMeter());
        Optional<Residence>optResidence = Optional.of(aResidence());
        MeasurementDto measurementDto = aMeasurementDto();


        when(meterService
                .getBySerialNumberAndPassword(aMeter().getSerialNumber(),
                        aMeter().getPassword()))
                .thenReturn(optMeter);

        when(residenceService.getByMeter(aMeter()))
                .thenReturn(optResidence);

        when(measurementRepository.save(any())).thenReturn(aMeasurement());


        try {
            Measurement measurement = measurementService.add(measurementDto);
            assertEquals(aMeasurement().getId(),measurement.getId());
        } catch (ResidenceNotExistsException e) {
            e.printStackTrace();
        } catch (MeterNotExistsException e) {
            e.printStackTrace();
        }



    }

    @Test
    public void addTestThrowsResidenceNotExistsException(){
        when(meterService
                .getBySerialNumberAndPassword(aMeter().getSerialNumber(),
                        aMeter().getPassword()))
                .thenReturn(Optional.of(aMeter()));

        when(residenceService.getByMeter(aMeter()))
                .thenReturn(Optional.empty());

        assertThrows(ResidenceNotExistsException.class,()->{
            measurementService.add(aMeasurementDto());
        });
    }

    @Test
    public void addTestThrowsMeterNotExistsException(){
        when(meterService
                .getBySerialNumberAndPassword(aMeter().getSerialNumber(),
                        aMeter().getPassword()))
                .thenReturn(Optional.empty());

        when(residenceService.getByMeter(aMeter()))
                .thenReturn(Optional.of(aResidence()));

        assertThrows(MeterNotExistsException.class,()->{
            measurementService.add(aMeasurementDto());
        });
    }

    @Test
    public void getAllTestOk(){
         when(measurementRepository.findAll(aPageable())).thenReturn(aMeasurementPage());

        Page<Measurement> measurements = measurementService.getAll(aPageable());

        assertEquals(aMeasurementPage()
                .getContent()
                .get(0)
                .getId()
                ,measurements
                        .getContent()
                        .get(0)
                        .getId());
    }

    @Test
    public void getAllTestEmptyPage(){
        when(measurementRepository.findAll(aPageable())).thenReturn(Page.empty(aPageable()));

        Page<Measurement> measurements = measurementService.getAll(aPageable());

        assertEquals(Page.empty(aPageable()).getContent(),measurements.getContent());

     }


    @Test
    public void getIntakeBetweenDateTestOk() throws NonExistentResourceException {
         LinkedList<Measurement> measurements = new LinkedList<>();
         measurements.add(aMeasurement());
         when(measurementRepository.findListOfIntakeByRangeOfDates(any(Long.class),any(LocalDateTime.class),any(LocalDateTime.class)))
                 .thenReturn(measurements);

         Optional<Intake> intake = measurementService.getIntakeByRangeOfDates(1L,LocalDateTime.now(),LocalDateTime.now());

         assertEquals(12.5,intake.get().getKwhPrice());
    }

    @Test
    public void getIntakeBetweenDateTestEmptyList(){
        LinkedList<Measurement> measurements = new LinkedList<>();
        when(measurementRepository.findListOfIntakeByRangeOfDates(any(Long.class),any(LocalDateTime.class),any(LocalDateTime.class)))
                .thenReturn(measurements);


        assertThrows(NonExistentResourceException.class,()->{
            measurementService.getIntakeByRangeOfDates(1L,LocalDateTime.now(),LocalDateTime.now());
        });
    }

    @Test
    public void getMeasurementsBetweenDatesTestOk(){

         when(measurementRepository.
                 findMeasurementsByRangeOfDate(anyLong(),
                         any(LocalDateTime.class),
                         any(LocalDateTime.class),
                         any(Pageable.class)))
         .thenReturn(aMeasurementPage());

        when(modelMapper.map(any(Measurement.class), eq(MeasurementsDto.class)))
                .thenReturn(aMeasurementsDto());

         Page<MeasurementsDto> measurements = measurementService.getMeasurementsByDateRange(1L,LocalDateTime.now(),LocalDateTime.now(),aPageable());

         assertEquals(aMeasurementsDto().getKwhPrice(),measurements.getContent().get(0).getKwhPrice());
    }

    @Test//preguntar pablo que onda esto
    public void getMeasurementsBetweenDatesTestEmptyPage(){
        when(measurementRepository.
                findMeasurementsByRangeOfDate(anyLong(),
                        any(LocalDateTime.class),
                        any(LocalDateTime.class),
                        any(Pageable.class)))
                .thenReturn(Page.empty(aPageable()));


        Page<MeasurementsDto> measurements = measurementService.getMeasurementsByDateRange(1L,LocalDateTime.now(),LocalDateTime.now(),aPageable());

        assertTrue(measurements.isEmpty());

    }

    @Test
    public void getMeasurementsBetweenDatesAndByResidenceTestOk(){
        when(measurementRepository.
                findByResidenceAndRangeOfDate(anyLong(),
                        any(LocalDateTime.class),
                        any(LocalDateTime.class),
                        any(Pageable.class)))
                .thenReturn(aMeasurementPage());

        when(modelMapper.map(any(Measurement.class), eq(MeasurementsDto.class)))
                .thenReturn(aMeasurementsDto());

        Page<MeasurementsDto> page = measurementService.getMeasurementsByResidenceAndRangeOfDate(1L,LocalDateTime.now(),LocalDateTime.now(),aPageable());

        assertEquals(aMeasurementsDtoPage().getContent().get(0).getKwhPrice(),page.getContent().get(0).getKwhPrice());
    }

}
