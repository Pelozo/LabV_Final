package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.model.Tariff;
import net.pelozo.FinalTPLab5DB2.repository.TariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TariffService {

    @Autowired
    TariffRepository tariffRepository;

    public ResponseEntity<List<Tariff>> getAll() {
        List<Tariff> tariffs = tariffRepository.findAll();
        if(!tariffs.isEmpty()){
            return ResponseEntity.ok(tariffs);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    public ResponseEntity<Tariff> save(Tariff tariff) {
        try{
            Tariff newTariff = tariffRepository.save(tariff);
            return ResponseEntity.ok(newTariff);
        } catch (DataIntegrityViolationException e) {
            System.out.println("tariff already exists");
            //e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    public ResponseEntity<Tariff> getById(Long id) {
        return tariffRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    public void deleteById(Long id) {
        tariffRepository.deleteById(id);
    }

    public ResponseEntity<Tariff> update(Tariff _tariff) {
        Optional<Tariff> tariff = tariffRepository.findById(_tariff.getId());
        if(tariff.isPresent()) {
            tariff.get().setName(_tariff.getName());
            tariff.get().setValue(_tariff.getValue());
            return save(tariff.get());
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
