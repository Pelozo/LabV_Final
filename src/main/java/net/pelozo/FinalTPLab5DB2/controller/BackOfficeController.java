package net.pelozo.FinalTPLab5DB2.controller;

import net.pelozo.FinalTPLab5DB2.model.Tariff;
import net.pelozo.FinalTPLab5DB2.service.BackofficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/backoffice")
public class BackOfficeController {

    private final TariffController tariffController;

    @Autowired
    public BackOfficeController(TariffController tariffController) {
        this.tariffController = tariffController;
    }

    @GetMapping("/tariffs/{id}")
    public ResponseEntity<Tariff> getTariff(@PathVariable long id){
        return tariffController.getById(id);
    }

    @GetMapping("/tariffs")
    public ResponseEntity<List<Tariff>> getAllTariffs(Pageable pageable){
        return tariffController.getAll(pageable);
    }


    //alta tarifa
    @PostMapping("/tariffs")
    public ResponseEntity<Tariff> addTariff(@RequestBody Tariff tariff){
        return tariffController.addTariff(tariff);
    }

    //baja tarifa
    @DeleteMapping("/tariffs/{id}")
    public ResponseEntity<String> deleteTariffById(@PathVariable long id){
        return tariffController.deleteById(id);
    }

    //modificacion tarifa
    @PutMapping("/tariffs")
    public ResponseEntity<Tariff> updateTariff(@RequestBody Tariff tariff){
        return tariffController.updateTariff(tariff);
    }




}
