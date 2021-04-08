package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.model.Client;
import net.pelozo.FinalTPLab5DB2.model.Tariff;
import net.pelozo.FinalTPLab5DB2.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public ResponseEntity getAll() {
        List<Client> clients = clientRepository.findAll();
        if(!clients.isEmpty()){
            return ResponseEntity.ok(clients);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    public ResponseEntity save(Client client){
        //try{
            Client newClient = clientRepository.save(client);
            return ResponseEntity.ok(newClient);
        //} catch (DataIntegrityViolationException e) {
        //    return ResponseEntity.status(HttpStatus.CONFLICT)
        //            .body("Username already in use");
        //}
    }

    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    public ResponseEntity<Client> getById(Long id) {
        return clientRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}
