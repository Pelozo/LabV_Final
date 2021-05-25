package net.pelozo.FinalTPLab5DB2.service;


import net.pelozo.FinalTPLab5DB2.model.Measurements;
import net.pelozo.FinalTPLab5DB2.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasurementService {

    @Autowired
    private MeasurementRepository measurementRepository;

    public Measurements add(Measurements measurements) {
        return measurementRepository.save(measurements);
    }

    public List<Measurements> getAll() {
        return measurementRepository.findAll();
    }
}
