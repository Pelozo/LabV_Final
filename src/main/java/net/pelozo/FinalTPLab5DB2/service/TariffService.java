package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.exception.NonExistentResourceException;
import net.pelozo.FinalTPLab5DB2.model.Tariff;
import net.pelozo.FinalTPLab5DB2.repository.TariffRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static net.pelozo.FinalTPLab5DB2.utils.Misc.parseDataConstraintEx;

@Service
public class TariffService {

    @Autowired
    TariffRepository tariffRepository;

    public Page<Tariff> getAll(Pageable pageable) {
        return tariffRepository.findAll(pageable);
    }

    public Tariff add(Tariff tariff) {
        Tariff newTariff = tariffRepository.save(tariff);
        return newTariff;
    }

    public Tariff getById(Long id) throws NonExistentResourceException {
        Optional<Tariff> tariff = tariffRepository.findById(id);
        if(tariff.isPresent()) {
            return tariff.get();
        }else{
            throw new NonExistentResourceException();
        }
    }

    public void deleteById(Long id) throws NonExistentResourceException {
        Optional<Tariff> tariff = tariffRepository.findById(id);
        if(tariff.isPresent()){
            tariffRepository.deleteById(id);
        }else{
            throw new NonExistentResourceException();
        }
    }

    public Tariff update(Tariff _tariff) throws NonExistentResourceException {
        Optional<Tariff> tariff = tariffRepository.findById(_tariff.getId());
        if(tariff.isPresent()) {
            tariff.get().setName(_tariff.getName());
            tariff.get().setValue(_tariff.getValue());
            return tariffRepository.save(tariff.get());
        }else{
            throw new NonExistentResourceException();
        }
    }
}
