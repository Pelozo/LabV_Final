package net.pelozo.FinalTPLab5DB2.service;


import net.pelozo.FinalTPLab5DB2.exception.ClientExistsException;
import net.pelozo.FinalTPLab5DB2.exception.ClientNotExistsException;
import net.pelozo.FinalTPLab5DB2.model.Client;
import net.pelozo.FinalTPLab5DB2.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static net.pelozo.FinalTPLab5DB2.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientServiceTest {

    ClientRepository clientRepository;
    ClientService clientService;

    @BeforeEach
    public void setUp(){
        clientRepository = mock(ClientRepository.class);
        clientService = new ClientService(clientRepository);
    }

    @Test
    public void addTestOk(){
        //when
        Client c = aClient();

        when(clientRepository.save(c)).thenReturn(c);

        Client cl = clientService.add(c);

        //then
        assertEquals(c.getDni(),cl.getDni());
    }

    @Test
    public void addTestError(){
        Client c = aClient();

        when(clientRepository.save(c)).thenThrow(ClientExistsException.class);

        Exception ex = assertThrows(ClientExistsException.class, () -> {
           clientService.add(c);
        });

        //then
        assertEquals("This client already exists!", ex.getMessage());
    }

    @Test
    public void deleteByIdTestError()  {
//        Client c = aClient();
//        try {
//            when(clientService.getById(c.getId())).thenThrow(ClientNotExistsException.class);
//        } catch (ClientNotExistsException e) {
//
//             e = assertThrows(ClientNotExistsException.class, () ->{
//                clientService.deleteById(c.getId());
//            });
//
//            assertEquals("This client does not exists!",e.getMessage());
//        }

        ClientNotExistsException ex = assertThrows(ClientNotExistsException.class, () -> {
            clientService.deleteById(aClient().getId());
        });

        assertEquals("This client does not exists!",ex.getMessage());

    }

    @Test
    public void getByIdTestOk() throws ClientNotExistsException {
        Optional<Client> client = Optional.of(aClient());

        when(clientRepository.findById(aClient().getId())).thenReturn(client);
        //when(client.isPresent()).thenReturn(true);

        Client c = clientService.getById(aClient().getId());

        assertEquals(client.get().getId(),c.getId());
    }

    @Test
    public void getByIdTestError(){

        ClientNotExistsException ex = assertThrows(ClientNotExistsException.class, () -> {
            clientService.getById(aClient().getId());
        });

        assertEquals("This client does not exists!",ex.getMessage());
    }

    @Test
    public void getAllTestOk(){
        Pageable pageable = aPageable();
        Page<Client> page = aClientPage();

        when(clientRepository.findAll(pageable)).thenReturn(page);

        Page<Client> response = clientService.getAll(pageable);

        assertEquals(page.getTotalElements(), response.getTotalElements());
        assertEquals(page.getContent().get(0).getDni(),response.getContent().get(0).getDni());

    }

    @Test
    public void getAllTestNoContent(){
        Pageable pageable = aPageable();
        Page<Client> page = Page.empty(pageable);

        when(clientRepository.findAll(pageable)).thenReturn(page);

        Page<Client> c = clientService.getAll(pageable);

        assertEquals(List.of(),c.getContent());
    }
}
