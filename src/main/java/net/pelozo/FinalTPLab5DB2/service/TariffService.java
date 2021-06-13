package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.exception.IdViolationException;
import net.pelozo.FinalTPLab5DB2.exception.NonExistentResourceException;
import net.pelozo.FinalTPLab5DB2.model.Tariff;
import net.pelozo.FinalTPLab5DB2.repository.TariffRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TariffService {


    TariffRepository tariffRepository;

    @Autowired
    public TariffService(TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }

    public Page<Tariff> getAll(Pageable pageable) {
        return tariffRepository.findAll(pageable);
    }

    public Tariff add(Tariff tariff) {
        return tariffRepository.save(tariff);
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

    public void update(Long id, Tariff _tariff) throws NonExistentResourceException, IdViolationException {
        Optional<Tariff> tariff = tariffRepository.findById(id);
        if(tariff.isPresent()){
            if(_tariff.getId() != null && !tariff.get().getId().equals(_tariff.getId())){
                throw new IdViolationException();
            }else{
                _tariff.setId(tariff.get().getId());
                tariffRepository.save(_tariff);
            }
        }else{
            throw new NonExistentResourceException();
        }
    }
}
