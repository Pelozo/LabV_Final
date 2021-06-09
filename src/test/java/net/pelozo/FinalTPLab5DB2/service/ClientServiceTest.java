package net.pelozo.FinalTPLab5DB2.service;


import net.pelozo.FinalTPLab5DB2.exception.ClientExistsException;
import net.pelozo.FinalTPLab5DB2.exception.ClientNotExistsException;
import net.pelozo.FinalTPLab5DB2.exception.InvalidCombinationUserPassword;
import net.pelozo.FinalTPLab5DB2.model.Client;
import net.pelozo.FinalTPLab5DB2.model.dto.ClientDto;
import net.pelozo.FinalTPLab5DB2.model.dto.UserDto;
import net.pelozo.FinalTPLab5DB2.repository.ClientRepository;
import net.pelozo.FinalTPLab5DB2.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static net.pelozo.FinalTPLab5DB2.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ClientServiceTest {

    ClientRepository clientRepository;
    ClientService clientService;
    ModelMapper modelMapper;

    @BeforeEach
    public void setUp(){
        clientRepository = mock(ClientRepository.class);
        modelMapper = mock(ModelMapper.class);
        clientService = new ClientService(clientRepository,modelMapper);
    }

    @Test
    public void addTestOk(){
        when(clientRepository.save(aClient())).thenReturn(aClient());

        Client cl = clientService.add(aClient());

        //then
        assertNotNull(cl);
        assertEquals(aClient().getDni(),cl.getDni());
    }

    @Test
    public void addTestError(){
        when(clientRepository.save(aClient())).thenThrow(ClientExistsException.class);

        assertThrows(ClientExistsException.class, () -> {
           clientService.add(aClient());
        });
    }

    @Test
    public void deleteByIdTestOk(){
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(aClient()));

        try {
            //when
            clientService.deleteById(1L);
            //then
            verify(clientRepository, times(1)).findById(aClient().getId());
        } catch (ClientNotExistsException e) {
            fail();
        }

    }

    @Test
    public void deleteByIdTestThrowsClientNotExistsException() {

        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

       assertThrows(ClientNotExistsException.class, () -> {
            clientService.deleteById(aClient().getId());
        });
    }

    @Test
    public void getByIdTestOk() throws ClientNotExistsException {
        Optional<Client> client = Optional.of(aClient());

        when(clientRepository.findById(aClient().getId())).thenReturn(client);

        Client c = clientService.getById(aClient().getId());

        assertEquals(client.get().getId(),c.getId());
    }

    @Test
    public void getByIdTestThrowsClientNotExistsException(){

        when(clientRepository.findById(aClient().getId())).thenReturn(Optional.empty());

        assertThrows(ClientNotExistsException.class, () -> {
            clientService.getById(aClient().getId());
        });

    }

    @Test
    public void getAllTestOk(){
        Page<Client> page = aClientPage();

        when(clientRepository.findAll(aPageable())).thenReturn(aClientPage());

        Page<Client> response = clientService.getAll(aPageable());

        assertEquals(page.getTotalElements(), response.getTotalElements());
        assertEquals(page.getContent().get(0).getDni(),response.getContent().get(0).getDni());

    }

    @Test
    public void loginTestOk() throws InvalidCombinationUserPassword {
        when(clientRepository.findByUsernameAndPassword(anyString(),anyString()))
                .thenReturn(aClient());
        when(modelMapper.map(any(Client.class),eq(UserDto.class)))
                .thenReturn(TestUtils.aUserDto());

        UserDto user = clientService.login("pepe","1234");

        assertNotNull(user);
        assertEquals(aClient().getUsername(),user.getUsername());
    }

    @Test
    public void loginTestThrowsInvalidCombinationUserPassword(){
        when(clientRepository.findByUsernameAndPassword(anyString(),anyString()))
                .thenReturn(null);

        assertThrows(InvalidCombinationUserPassword.class,()->{
            clientService.login("pepe","1234");
        });
    }

    @Test
    public void getTopTenConsumersTestOk(){
        when(clientRepository.findTopTenConsumersBetweenDates(any(LocalDate.class),any(LocalDate.class)))
                .thenReturn(List.of(aClient()));

        when(modelMapper.map(any(Client.class),eq(ClientDto.class)))
                .thenReturn(aClientDto());

        List<ClientDto> clients = clientService.getTopTenConsumers(LocalDate.now(),LocalDate.now());

        assertFalse(clients.isEmpty());
        assertEquals(aClient().getId(),clients.get(0).getId());
    }

}
