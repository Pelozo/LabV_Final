package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.model.Client;
import net.pelozo.FinalTPLab5DB2.model.Tariff;
import net.pelozo.FinalTPLab5DB2.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public List<Client> getAll() {
        List<Client> clients = clientRepository.findAll();
        if(!clients.isEmpty()){
            return clients;
        }else{
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    public Client add(Client client){
        try{
            return clientRepository.save(client);
        }catch (DataIntegrityViolationException e) {
            System.out.println("client already exists");
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    public Client getById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()) {
            return client.get();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
