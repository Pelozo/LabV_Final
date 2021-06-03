package net.pelozo.FinalTPLab5DB2.controller;

import net.pelozo.FinalTPLab5DB2.exception.NonExistentResourceException;
import net.pelozo.FinalTPLab5DB2.model.Tariff;
import net.pelozo.FinalTPLab5DB2.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/tariff")
public class TariffController {

    @Autowired
    TariffService tariffService;

    @GetMapping
    public ResponseEntity<List<Tariff>> getAll(Pageable pageable){
        Page<Tariff> tariffs = tariffService.getAll(pageable);
        return ResponseEntity
                .status(tariffs.isEmpty() ? HttpStatus.NO_CONTENT: HttpStatus.OK)
                .header("X-Total-Pages", String.valueOf(tariffs.getTotalPages()))
                .header("X-Total-Content",String.valueOf(tariffs.getTotalElements()))
                .body(tariffs.getContent());
    }

    @PostMapping
    public ResponseEntity<Tariff> addTariff(@RequestBody Tariff newTariff){
        Tariff tariff =  tariffService.add(newTariff);
        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tariff.getId())
                .toUri())
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tariff> getById(@PathVariable Long id) throws NonExistentResourceException{
        Tariff tariff = tariffService.getById(id);
        return ResponseEntity.ok(tariff);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) throws  NonExistentResourceException{
        tariffService.deleteById(id);
        return ResponseEntity.ok("");
    }

    @PutMapping
    public ResponseEntity<Tariff> updateTariff(@RequestBody Tariff tariff) {
        Tariff t = tariffService.update(tariff);
        return ResponseEntity.ok(t);
    }


}
