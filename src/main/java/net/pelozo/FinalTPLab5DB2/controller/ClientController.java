package net.pelozo.FinalTPLab5DB2.controller;


import net.pelozo.FinalTPLab5DB2.dto.ClientDto;
import net.pelozo.FinalTPLab5DB2.exception.ClientNotExistsException;
import net.pelozo.FinalTPLab5DB2.model.Client;
import net.pelozo.FinalTPLab5DB2.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping
    public ResponseEntity<Page<ClientDto>> getAll(
            @PageableDefault(page = 0, size = 10) Pageable pageable){

        Page<ClientDto> page =  clientService.getAll(pageable).map(ClientDto::from);

        return ResponseEntity
                .status(page.isEmpty() ? HttpStatus.NO_CONTENT: HttpStatus.OK)
                .body(page);
    }

    @PostMapping
    public ResponseEntity<Client> add(@RequestBody Client client){
        Client c = clientService.add(client);

        return ResponseEntity.created(ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(c.getId())
                    .toUri())
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) throws ClientNotExistsException {
        clientService.deleteById(id);
        return ResponseEntity.ok().body("Client successfully deleted! ");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getById(@PathVariable Long id) throws ClientNotExistsException {
        //return ResponseEntity.ok(ClientDto.from(clientService.getById(id)));
        ClientDto cd = new ModelMapper().map(clientService.getById(id),ClientDto.class);

        return ResponseEntity.ok(cd);
    }

    //consultar facturas por fecha

    //consultar facturas impagas

    //consultar consumo por rango de fechas

    //consultar mediciones por rango de fecha


}
