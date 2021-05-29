package net.pelozo.FinalTPLab5DB2.service;


import net.pelozo.FinalTPLab5DB2.exception.MeterNotExistsException;
import net.pelozo.FinalTPLab5DB2.exception.ResidenceNotExistsException;
import net.pelozo.FinalTPLab5DB2.model.Measurement;
import net.pelozo.FinalTPLab5DB2.model.Meter;
import net.pelozo.FinalTPLab5DB2.model.Residence;
import net.pelozo.FinalTPLab5DB2.model.dto.MeasurementDto;
import net.pelozo.FinalTPLab5DB2.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class MeasurementService {

    @Autowired
    private MeasurementRepository measurementRepository;
    @Autowired
    private MeterService meterService;
    @Autowired
    private ResidenceService residenceService;

    public Measurement add(MeasurementDto measurement) throws ResidenceNotExistsException, MeterNotExistsException {

        Optional<Meter> m = meterService.getBySerialNumberAndPassword(measurement.getSerialNumber(),measurement.getPassword());

        if(m.isPresent()){
            Optional<Residence> r = residenceService.getByMeter(m.get());
            if(r.isPresent()) {
                Measurement me = Measurement.builder()
                        .residence(r.get())
                        .date(measurement.getDate())
                        .kwhValue(measurement.getValue())
                        .build();
                return measurementRepository.save(me);
            }else{
                throw new ResidenceNotExistsException();
            }
        }else {
            throw new MeterNotExistsException();
        }

        //TODO check meterId exists, check password is correct, check is assigned to a house,



        //return measurementRepository.save(me);

    }

    public Page<Measurement> getAll(Pageable pageable) {
        return measurementRepository.findAll(pageable);
    }

//    public Page<Measurement> getMeasurementsByRangeOfDates(long id, LocalDateTime from, LocalDateTime to, Pageable pageable) {
//        return measurementRepository.findMeasurementsByRangeOfDates(id,from,to,pageable);
//    }
}
