package net.pelozo.FinalTPLab5DB2.controller;

import net.pelozo.FinalTPLab5DB2.model.Tariff;
import net.pelozo.FinalTPLab5DB2.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tariff")
public class TariffController {

    @Autowired
    TariffService tariffService;

    @GetMapping
    public List<Tariff> getAll(){
        return tariffService.getAll();
    }

    @PostMapping
    public Tariff addTariff(@RequestBody Tariff tariff){
        return tariffService.add(tariff);
    }

    @GetMapping("/{id}")
    public Tariff getById(@PathVariable Long id){
        return tariffService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        tariffService.deleteById(id);
    }

    @PutMapping
    public Tariff updateTariff(@RequestBody Tariff tariff) {
        return tariffService.update(tariff);
    }


}
