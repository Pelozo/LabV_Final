package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.model.Tariff;
import net.pelozo.FinalTPLab5DB2.repository.TariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TariffService {

    @Autowired
    TariffRepository tariffRepository;

    public List<Tariff> getAll() {
        List<Tariff> tariffs = tariffRepository.findAll();
        if(!tariffs.isEmpty()){
           return tariffs;
        }else{
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    public Tariff add(Tariff tariff) {
        try{
            Tariff newTariff = tariffRepository.save(tariff);
            return newTariff;
        }catch (DataIntegrityViolationException e) {
            System.out.println("tariff already exists");
            //e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public Tariff getById(Long id) {
        Optional<Tariff> tariff = tariffRepository.findById(id);
        if(tariff.isPresent()) {
            return tariff.get();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void deleteById(Long id) {
        tariffRepository.deleteById(id);
    }

    public Tariff update(Tariff _tariff) {
        Optional<Tariff> tariff = tariffRepository.findById(_tariff.getId());
        if(tariff.isPresent()) {
            tariff.get().setName(_tariff.getName());
            tariff.get().setValue(_tariff.getValue());
            return tariffRepository.save(tariff.get());
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
