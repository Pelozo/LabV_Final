package net.pelozo.FinalTPLab5DB2.controller;

import net.pelozo.FinalTPLab5DB2.exception.ClientNotExistsException;
import net.pelozo.FinalTPLab5DB2.exception.IdViolationException;
import net.pelozo.FinalTPLab5DB2.exception.InvalidResourceIdException;
import net.pelozo.FinalTPLab5DB2.exception.NonExistentResourceException;
import net.pelozo.FinalTPLab5DB2.model.Meter;
import net.pelozo.FinalTPLab5DB2.model.Residence;
import net.pelozo.FinalTPLab5DB2.model.dto.MeterDto;
import net.pelozo.FinalTPLab5DB2.model.dto.NewResidenceDto;
import net.pelozo.FinalTPLab5DB2.model.dto.ResidenceDto;
import net.pelozo.FinalTPLab5DB2.service.MeterService;
import net.pelozo.FinalTPLab5DB2.service.ResidenceService;
import net.pelozo.FinalTPLab5DB2.utils.EntityURLBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import static net.pelozo.FinalTPLab5DB2.utils.TestUtils.*;
import static net.pelozo.FinalTPLab5DB2.utils.TestUtils.aResidencePage;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ResidenceControllerTest {


    ResidenceService residenceService;
    ResidenceController residenceController;
    ModelMapper modelMapper;

    @BeforeEach
    public void setUp(){
        residenceService = mock(ResidenceService.class);
        modelMapper = mock(ModelMapper.class);
        residenceController = new ResidenceController(residenceService, modelMapper);
    }

    @Test
    public void getAllOkTest(){
        when(residenceService.getAll(any(Pageable.class))).thenReturn(aResidenceDtoPage());

        ResponseEntity<List<ResidenceDto>> response = residenceController.getAll(aPageable());

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(
                aResidenceDtoPage().getTotalElements(),
                Long.valueOf(response.getHeaders().get("X-Total-Pages").get(0))
        );

        assertEquals(
                aResidenceDtoPage().getContent().get(0).getAddress(),
                response.getBody().get(0).getAddress()
        );
    }

    @Test
    public void getByClientOkTest(){
        try {
            when(residenceService.getByClient(anyLong(), any(Pageable.class))).thenReturn(aResidencePage());

            when(modelMapper.map(any(Residence.class), eq(ResidenceDto.class))).thenReturn(aResidenceDto());

            ResponseEntity<List<ResidenceDto>> response = residenceController.getResidenceByClient(1L, aPageable());

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(aResidencePage().getTotalElements(),
                    Long.valueOf(response.getHeaders().get("X-Total-Pages").get(0))
            );

            assertEquals(
                    aResidenceDtoPage().getContent().get(0).getId(),
                    response.getBody().get(0).getId()
            );

        } catch (ClientNotExistsException e) {
            fail();
        }
    }

    @Test
    public void deleteByIdOkTest(){
        try {
            ResponseEntity<String> response = residenceController.deleteById(1L);

            verify(residenceService, times(1)).deleteById(anyLong());
            assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        } catch (NonExistentResourceException e) {
            fail();
        }
    }

    @Test
    public void deleteById_ThrowsNonExistentResourceExceptionTest() throws NonExistentResourceException {
        doThrow(new NonExistentResourceException()).when(residenceService).deleteById(anyLong());

        assertThrows(NonExistentResourceException.class, () ->{
            residenceController.deleteById(1L);
        });
    }


    @Test
    public void updateMeterOkTest(){

        try {
            ResponseEntity response = residenceController.updateResidence(1L, aNewResidenceDto());

            assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
            verify(residenceService, times(1)).update(anyLong(), any(NewResidenceDto.class));

        } catch (NonExistentResourceException | IdViolationException | InvalidResourceIdException e) {
            fail();
        }
    }

    @Test
    public void updateMeter_ThrowsNonExistentResourceExceptionTest() throws IdViolationException, NonExistentResourceException, InvalidResourceIdException {
        doThrow(new NonExistentResourceException()).when(residenceService).update(anyLong(), any(NewResidenceDto.class));

        assertThrows(NonExistentResourceException.class, () ->{
            residenceController.updateResidence(1L, aNewResidenceDto());
        });
    }

    @Test
    public void updateMeter_ThrowsInvalidResourceIdExceptionTest() throws NonExistentResourceException, InvalidResourceIdException {
        doThrow(new InvalidResourceIdException("")).when(residenceService).update(anyLong(), any(NewResidenceDto.class));

        assertThrows(InvalidResourceIdException.class, () ->{
            residenceController.updateResidence(1L, aNewResidenceDto());
        });
    }



}
