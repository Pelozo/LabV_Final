package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.exception.ClientExistsException;
import net.pelozo.FinalTPLab5DB2.model.Client;
import net.pelozo.FinalTPLab5DB2.model.PaginationResponse;
import net.pelozo.FinalTPLab5DB2.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

@Service
public class ClientService {

    //private final static String CLIENT_URL = "client";
    @Autowired
    ClientRepository clientRepository;

    public PaginationResponse<Client> getAll(Pageable pageable) {

        Page<Client> clientPage = clientRepository.findAll(pageable);

        if(!clientPage.isEmpty()){
            return new PaginationResponse<>(clientPage.getContent(),
                                            clientPage.getTotalPages(),
                                            clientPage.getTotalElements());
        }else{
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    public ResponseEntity<Client> add(Client client) {

        try{
            Client c = clientRepository.save(client);

            return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(c.getId()).toUri()).build();
        }catch (DataIntegrityViolationException e) {
            //System.out.println("client already exists");
            //e.printStackTrace();
            throw new ClientExistsException(e.getMessage());
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
