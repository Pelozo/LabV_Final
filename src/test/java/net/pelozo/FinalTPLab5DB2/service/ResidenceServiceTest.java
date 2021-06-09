package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.exception.ClientNotExistsException;
import net.pelozo.FinalTPLab5DB2.exception.NonExistentResourceException;
import net.pelozo.FinalTPLab5DB2.model.Residence;
import net.pelozo.FinalTPLab5DB2.model.dto.ResidenceDto;
import net.pelozo.FinalTPLab5DB2.repository.ResidenceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static net.pelozo.FinalTPLab5DB2.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ResidenceServiceTest {

    ResidenceRepository residenceRepository;
    ModelMapper modelMapper;
    ClientService clientService;
    ResidenceService residenceService;

    @BeforeEach
    public void setUp(){
        residenceRepository = mock(ResidenceRepository.class);
        modelMapper = mock(ModelMapper.class);
        clientService = mock(ClientService.class);
        residenceService = new ResidenceService(residenceRepository,modelMapper,clientService);
    }

    @Test
    public void getAllTestOk(){
        when(residenceRepository.findAll(any(Pageable.class)))
                .thenReturn(aResidencePage());
        when(modelMapper.map(any(Residence.class),eq(ResidenceDto.class)))
                .thenReturn(aResidenceDto());

        Page<ResidenceDto> residences = residenceService.getAll(aPageable());

        assertNotNull(residences);
        assertEquals(aResidenceDto().getAddress(),residences.getContent().get(0).getAddress());
    }

    @Test
    public void getByMeterTestOk(){
        when(residenceRepository.findByMeterId(anyLong()))
            .thenReturn(Optional.of(aResidence()));

        Optional<Residence> residence = residenceService.getByMeter(aMeter());

        assertNotNull(residence);
        assertEquals(aResidence().getId(),residence.get().getId());
    }

    @Test
    public void addTestOk() throws ClientNotExistsException {
        when(clientService.getById(anyLong()))
                .thenReturn(aClient());

        when(residenceRepository.save(any(Residence.class)))
                .thenReturn(aResidence());
        Residence residence = residenceService.add(1L,aResidence());

        assertNotNull(residence);
        assertEquals(aClient().getId(),
                residence.getClient().getId());
    }

    @Test
    public void addTestThrowsClientNotExistsException() throws ClientNotExistsException {

        when(clientService.getById(anyLong())).thenThrow(ClientNotExistsException.class);

        assertThrows(ClientNotExistsException.class,()->{
            residenceService.add(1L,aResidence());
        });

    }

    @Test
    public void deleteByIdTestOk() throws NonExistentResourceException {
        when(residenceRepository.findById(anyLong()))
                .thenReturn(Optional.of(aResidence()));

        residenceService.deleteById(1L);
        verify(residenceRepository,times(1)).deleteById(1L);
    }

    @Test
    public void deleteByIdTestThrowsNonExistentResourceException(){
        when(residenceRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        assertThrows(NonExistentResourceException.class,()->{
            residenceService.deleteById(1L);
        });
    }

    @Test
    public void updateTestOk() throws NonExistentResourceException {
        when(residenceRepository.findById(anyLong()))
                .thenReturn(Optional.of(aResidence()));
        when(residenceRepository.save(any(Residence.class)))
                .thenReturn(aResidence());
        Residence residence = residenceService.update(1L,aResidence());

        assertNotNull(residence);
        assertEquals(aResidence().getId(),residence.getId());
    }

    @Test
    public void updateTestThrowsNonExistentResourceException(){
        when(residenceRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(NonExistentResourceException.class,()->{
            residenceService.update(1L,aResidence());
        });
    }

}