package net.pelozo.FinalTPLab5DB2.controller;


import net.pelozo.FinalTPLab5DB2.model.Client;
import net.pelozo.FinalTPLab5DB2.model.PaginationResponse;
import net.pelozo.FinalTPLab5DB2.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping
    public PaginationResponse<Client> getAll(@RequestParam Pageable pageable){
        return clientService.getAll(pageable);
    }

    @PostMapping
    public ResponseEntity<Client> add(@RequestBody Client client){

        return clientService.add(client);
//        PostResponse
//                .builder()
//                .status(HttpStatus.CREATED)
//                    .url(EntityURLBuilder.buildURL(CLIENT_URL, c.getDni()))
//            .build();
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
