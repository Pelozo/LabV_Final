package net.pelozo.FinalTPLab5DB2.controller;

import net.pelozo.FinalTPLab5DB2.exception.IdViolationException;
import net.pelozo.FinalTPLab5DB2.exception.InvalidIdException;
import net.pelozo.FinalTPLab5DB2.exception.NonExistentResourceException;
import net.pelozo.FinalTPLab5DB2.model.Meter;
import net.pelozo.FinalTPLab5DB2.model.dto.MeterDto;
import net.pelozo.FinalTPLab5DB2.service.MeasurementService;
import net.pelozo.FinalTPLab5DB2.service.MeterService;
import net.pelozo.FinalTPLab5DB2.utils.EntityURLBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import static net.pelozo.FinalTPLab5DB2.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class MeterControllerTest {

    MeterService meterService;

    MeterController meterController;

    @BeforeEach
    public void setUp(){
        meterService = mock(MeterService.class);
        meterController = new MeterController(meterService);
    }

    @Test
    public void getAllOkTest(){
        when(meterService.getAll(any(Pageable.class))).thenReturn(aMeterDtoPage());

        ResponseEntity<List<MeterDto>> response = meterController.getAll(aPageable());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(aMeterDtoPage().getTotalElements(), Long.valueOf(response.getHeaders().get("X-Total-Pages").get(0)));
        assertEquals(
                aMeterDtoPage().getContent().get(0).getSerialNumber(),
                response.getBody().get(0).getSerialNumber()
        );
    }

    @Test
    public void addOkTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(meterService.add(any(Meter.class))).thenReturn(aMeterDto());
        ResponseEntity response = meterController.add(aMeter());


        assertEquals(
                HttpStatus.CREATED.value(),
                response.getStatusCodeValue());

        assertEquals(
                EntityURLBuilder.buildURL("meters", aMeter().getId()).toString(),
                response.getHeaders().get("Location").get(0)
        );
    }

    @Test
    public void getByIdOkTest(){
        try {
            when(meterService.getById(anyLong())).thenReturn(aMeterDto());

            ResponseEntity<MeterDto> response = meterController.getById(1L);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(aMeterDto().getSerialNumber(), response.getBody().getSerialNumber());

        } catch (NonExistentResourceException e) {
            fail();
        }
    }

    @Test
    public void getById_ThrowsNonExistentResourceExceptionTest() throws NonExistentResourceException {
        when(meterService.getById(anyLong())).thenThrow(new NonExistentResourceException());
        assertThrows(NonExistentResourceException.class, () ->{
            meterController.getById(1L);
        });
    }

    @Test
    public void deleteByIdOkTest(){
        try {
            ResponseEntity<String> response = meterController.deleteById(1L);

            verify(meterService, times(1)).deleteById(anyLong());
            assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        } catch (NonExistentResourceException e) {
            fail();
        }
    }

    @Test
    public void deleteById_ThrowsNonExistentResourceExceptionTest() throws NonExistentResourceException {
        doThrow(new NonExistentResourceException()).when(meterService).deleteById(anyLong());

        assertThrows(NonExistentResourceException.class, () ->{
            meterController.deleteById(1L);
        });
    }

    @Test
    public void updateMeterOkTest(){

        try {
            ResponseEntity response = meterController.updateMeter(1L, aMeter());

            assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
            verify(meterService, times(1)).update(anyLong(), any(Meter.class));

        } catch (NonExistentResourceException | IdViolationException | InvalidIdException e) {
            fail();
        }
    }

    @Test
    public void updateMeter_ThrowsNonExistentResourceExceptionTest() throws IdViolationException, NonExistentResourceException, InvalidIdException {
        doThrow(new NonExistentResourceException()).when(meterService).update(anyLong(), any(Meter.class));

        assertThrows(NonExistentResourceException.class, () ->{
            meterController.updateMeter(1L, aMeter());
        });

    }

    @Test
    public void updateMeter_ThrowsIdViolationExceptionTest() throws IdViolationException, NonExistentResourceException, InvalidIdException {
        doThrow(new IdViolationException()).when(meterService).update(anyLong(), any(Meter.class));

        assertThrows(IdViolationException.class, () ->{
            meterController.updateMeter(1L, aMeter());
        });
    }
}
