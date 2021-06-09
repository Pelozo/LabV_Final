package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.exception.ClientExistsException;
import net.pelozo.FinalTPLab5DB2.exception.ClientNotExistsException;
import net.pelozo.FinalTPLab5DB2.exception.InvalidCombinationUserPassword;
import net.pelozo.FinalTPLab5DB2.model.Client;
import net.pelozo.FinalTPLab5DB2.model.dto.ClientDto;
import net.pelozo.FinalTPLab5DB2.model.dto.UserDto;
import net.pelozo.FinalTPLab5DB2.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    //private final static String CLIENT_URL = "client";

    ClientRepository clientRepository;
    ModelMapper modelMapper;

    @Autowired
    public ClientService(ClientRepository clientRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
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

    public UserDto login(String username, String password) throws InvalidCombinationUserPassword {
        Client c =  clientRepository.findByUsernameAndPassword(username, password);

        if(c != null){
            return modelMapper.map(c,UserDto.class);
        }else {
            throw new InvalidCombinationUserPassword();
        }
    }

    public List<ClientDto> getTopTenConsumers(LocalDate from, LocalDate to){
        return clientRepository.findTopTenConsumersBetweenDates(from,to).stream().map(o -> modelMapper.map(o, ClientDto.class)).collect(Collectors.toList());
    }
}


