package net.pelozo.FinalTPLab5DB2.controller;

import net.pelozo.FinalTPLab5DB2.exception.InvalidDateException;
import net.pelozo.FinalTPLab5DB2.exception.MeterNotExistsException;
import net.pelozo.FinalTPLab5DB2.exception.ResidenceNotExistsException;
import net.pelozo.FinalTPLab5DB2.model.dto.MeasurementsDto;
import net.pelozo.FinalTPLab5DB2.service.ClientService;
import net.pelozo.FinalTPLab5DB2.service.InvoiceService;
import net.pelozo.FinalTPLab5DB2.service.MeasurementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static net.pelozo.FinalTPLab5DB2.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MeasurementControllerTest {


    MeasurementService measurementService;

    MeasurementController measurementController;

    @BeforeEach
    public void setUp(){
        measurementService = mock(MeasurementService.class);
        measurementController = new MeasurementController(measurementService);
    }

    @Test
    public void addMeasurementTest() throws InvalidDateException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        try {
            when(measurementService.add(any())).thenReturn(aMeasurement());
            ResponseEntity<String> response = measurementController.add(aMeasurementDto());
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
        } catch (ResidenceNotExistsException | MeterNotExistsException e) {
            fail();
        }
    }

    @Test
    public void getAllTest(){
        ModelMapper mm = mock(ModelMapper.class);
        when(measurementService.getAll(any(Pageable.class))).thenReturn(aMeasurementPage());
        when(mm.map(any(), any())).thenReturn(aMeasurementDto());

        ResponseEntity<List<MeasurementsDto>> response = measurementController.getAllMeasurements(aPageable());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(
                aMeasurementPage().getContent().get(0).getKwhPrice(),
                response.getBody().get(0).getKwhPrice()
        );
    }

    @Test
    public void getByResidenceOkTest(){
        when(measurementService.getMeasurementsByResidenceAndRangeOfDate(
                anyLong(),
                any(LocalDateTime.class),
                any(LocalDateTime.class),
                any(Pageable.class))
        ).thenReturn(aMeasurementsDtoPage());

        ResponseEntity<List<MeasurementsDto>> response = measurementController.getMeasurementsByResidenceAndRangeOfDates(
                1L,
                LocalDateTime.of(2020, 1, 1, 1, 1),
                LocalDateTime.of(2020, 1, 1, 1, 1),
                aPageable()
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(
                aMeasurementsDtoPage().getTotalElements(),
                Long.valueOf(response.getHeaders().get("X-Total-Pages").get(0))
        );

        assertEquals(
                aMeasurementsDtoPage().getContent().get(0).getKwhPrice(),
                response.getBody().get(0).getKwhPrice()
        );
    }

}
