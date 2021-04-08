package net.pelozo.FinalTPLab5DB2.controller;


import net.pelozo.FinalTPLab5DB2.model.Client;
import net.pelozo.FinalTPLab5DB2.model.Tariff;
import net.pelozo.FinalTPLab5DB2.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping
    public List<Client> getAll(){
        return clientService.getAll();
    }

    @PostMapping
    public Client add(@RequestBody Client client){
        return clientService.add(client);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        clientService.deleteById(id);
    }

    @GetMapping("/{id}")
    public Client getById(@PathVariable Long id){
        return clientService.getById(id);
    }


}
