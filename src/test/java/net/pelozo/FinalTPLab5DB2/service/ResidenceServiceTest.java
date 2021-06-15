package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.exception.ClientNotExistsException;
import net.pelozo.FinalTPLab5DB2.exception.NonExistentResourceException;
import net.pelozo.FinalTPLab5DB2.model.Residence;
import net.pelozo.FinalTPLab5DB2.model.dto.ResidenceDto;
import net.pelozo.FinalTPLab5DB2.repository.ClientRepository;
import net.pelozo.FinalTPLab5DB2.repository.MeterRepository;
import net.pelozo.FinalTPLab5DB2.repository.ResidenceRepository;
import net.pelozo.FinalTPLab5DB2.repository.TariffRepository;
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
    TariffRepository tariffRepository;
    MeterRepository meterRepository;
    ClientRepository clientRepository;

    @BeforeEach
    public void setUp(){
        residenceRepository = mock(ResidenceRepository.class);
        modelMapper = mock(ModelMapper.class);
        clientService = mock(ClientService.class);
        tariffRepository = mock(TariffRepository.class);
        meterRepository = mock(MeterRepository.class);
        clientRepository = mock(ClientRepository.class);
        residenceService = new ResidenceService(residenceRepository,modelMapper,clientService, tariffRepository, meterRepository, clientRepository);
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
        Residence residence = residenceService.update(1L,aNewResidenceDto());

        assertNotNull(residence);
        assertEquals(aResidence().getId(),residence.getId());
    }

    @Test
    public void updateTestThrowsNonExistentResourceException(){
        when(residenceRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(NonExistentResourceException.class,()->{
            residenceService.update(1L,aNewResidenceDto());
        });
    }

    @Test
    public void getByClientOkTest(){

        try {
            when(clientService.getById(anyLong())).thenReturn(aClient());
            when(residenceRepository.findAllByClientId(anyLong(), any(Pageable.class))).thenReturn(aResidencePage());

            Page<Residence> residences = residenceService.getByClient(1L, aPageable());

            assertTrue(residences.hasContent());
            assertEquals(aResidence().getId(),residences.getContent().get(0).getId());

        } catch (ClientNotExistsException e) {
            fail();
        }
    }

    @Test
    public void getByClient_ThrowsClientNotExistsExceptionTest() throws ClientNotExistsException {

        when(clientService.getById(anyLong())).thenReturn(null);

        assertThrows(ClientNotExistsException.class,()->{
            residenceService.getByClient(1L,aPageable());
        });


    }


}
