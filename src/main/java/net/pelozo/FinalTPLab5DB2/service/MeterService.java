package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.model.Meter;
import net.pelozo.FinalTPLab5DB2.repository.MeterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MeterService {

    @Autowired
    private MeterRepository meterRepository;

    public Optional<Meter> getBySerialNumberAndPassword(String serialNumber, String password){
        return meterRepository.findBySerialNumberAndPassword(serialNumber,password);
    }
}