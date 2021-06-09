package net.pelozo.FinalTPLab5DB2.controller;

import net.pelozo.FinalTPLab5DB2.exception.IdViolationException;
import net.pelozo.FinalTPLab5DB2.exception.NonExistentResourceException;
import net.pelozo.FinalTPLab5DB2.model.Tariff;
import net.pelozo.FinalTPLab5DB2.service.TariffService;
import net.pelozo.FinalTPLab5DB2.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static net.pelozo.FinalTPLab5DB2.utils.MyResponse.response;

@RestController
@RequestMapping("/tariff")
public class TariffController {


    TariffService tariffService;

    @Autowired
    public TariffController(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    @GetMapping
    public ResponseEntity<List<Tariff>> getAll(Pageable pageable){
        Page<Tariff> tariffs = tariffService.getAll(pageable);
        return response(tariffs);
    }

    @PostMapping
    public ResponseEntity<String> addTariff(@RequestBody Tariff newTariff){
        Tariff tariff =  tariffService.add(newTariff);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(EntityURLBuilder.buildURL("tariffs",tariff.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .body("");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tariff> getById(@PathVariable Long id) throws NonExistentResourceException{
        Tariff tariff = tariffService.getById(id);
        return ResponseEntity.ok(tariff);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) throws NonExistentResourceException{
        tariffService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateTariff(@PathVariable Long id,
                                               @RequestBody Tariff tariff) throws NonExistentResourceException, IdViolationException {
        tariffService.update(id, tariff);
        return ResponseEntity.accepted().build();
    }

}
