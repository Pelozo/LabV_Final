package net.pelozo.FinalTPLab5DB2.service;


import net.pelozo.FinalTPLab5DB2.model.Measurement;
import net.pelozo.FinalTPLab5DB2.model.dto.MeasurementDto;
import net.pelozo.FinalTPLab5DB2.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MeasurementService {

    @Autowired
    private MeasurementRepository measurementRepository;

    public Measurement add(MeasurementDto measurement){

        //TODO check meterId exists, check password is correct, check is assigned to a house,

        return Measurement.builder().id(1).date(LocalDateTime.now()).kwhPrice(2).kwhValue(3).build();

    }

    public Page<Measurement> getAll(Pageable pageable) {
        return measurementRepository.findAll(pageable);
}
