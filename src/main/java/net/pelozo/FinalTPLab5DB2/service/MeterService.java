package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.exception.ClientNotExistsException;
import net.pelozo.FinalTPLab5DB2.exception.IdViolationException;
import net.pelozo.FinalTPLab5DB2.exception.NonExistentResourceException;
import net.pelozo.FinalTPLab5DB2.model.Client;
import net.pelozo.FinalTPLab5DB2.model.Meter;
import net.pelozo.FinalTPLab5DB2.model.Tariff;
import net.pelozo.FinalTPLab5DB2.model.dto.MeterDto;
import net.pelozo.FinalTPLab5DB2.repository.MeterRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MeterService {


    private final MeterRepository meterRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MeterService(MeterRepository meterRepository, ModelMapper modelMapper) {
        this.meterRepository = meterRepository;
        this.modelMapper = modelMapper;
    }


    public Optional<Meter> getBySerialNumberAndPassword(String serialNumber, String password){
        return meterRepository.findBySerialNumberAndPassword(serialNumber,password);
    }

    public MeterDto add(Meter meter){
        return modelMapper.map(meterRepository.save(meter),MeterDto.class);
    }

    public Page<MeterDto> getAll(Pageable pageable) {
        return meterRepository.findAll(pageable).map(meter -> modelMapper.map(meter,MeterDto.class));
    }

    public void deleteById(Long id ) throws NonExistentResourceException{
        Optional<Meter> meter = meterRepository.findById(id);
        if(meter.isPresent()){
            meterRepository.deleteById(id);
        }else{
            throw new NonExistentResourceException();
        }
    }

    public void update(Long id, Meter _meter) throws NonExistentResourceException, IdViolationException {
        Optional<Meter> meter = meterRepository.findById(id);
        if(meter.isPresent()){
            if(_meter.getId() != null && !meter.get().getId().equals(_meter.getId())){
                throw new IdViolationException();
            }else{
                _meter.setId(meter.get().getId());
                meterRepository.save(_meter);
            }
        }else{
            throw new NonExistentResourceException();
        }
    }

    public MeterDto getById(Long id) throws NonExistentResourceException {
        Optional<Meter> meter = meterRepository.findById(id);
        if(meter.isPresent()) {
            return modelMapper.map(meter.get(), MeterDto.class);
        }else{
            throw new NonExistentResourceException();
        }
    }
}
