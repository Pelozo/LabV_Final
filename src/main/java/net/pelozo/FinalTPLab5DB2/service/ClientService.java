package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.exception.ClientExistsException;
import net.pelozo.FinalTPLab5DB2.exception.ClientNotExistsException;
import net.pelozo.FinalTPLab5DB2.model.Client;
import net.pelozo.FinalTPLab5DB2.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    //private final static String CLIENT_URL = "client";

    ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client add(Client client) {
        try{
            return clientRepository.save(client);
        }catch(DataIntegrityViolationException e){
            throw new ClientExistsException();
        }
    }

    public void deleteById(Long id) throws ClientNotExistsException {
        Client c = getById(id);
        if(c == null){
            throw new ClientNotExistsException();
        }else{
            clientRepository.delete(c);
        }
    }

    public Client getById(Long id) throws ClientNotExistsException {
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()) {
            return client.get();
        }else{
            throw new ClientNotExistsException();
        }
    }

    public Page<Client> getAll(Pageable pageable){
        return clientRepository.findAll(pageable);
    }

    public Client login(String username, String password) {
        return clientRepository.findByUsernameAndPassword(username, password);
    }
}


