package net.pelozo.FinalTPLab5DB2.service;


import net.pelozo.FinalTPLab5DB2.exception.MeterNotExistsException;
import net.pelozo.FinalTPLab5DB2.exception.NonExistentResourceException;
import net.pelozo.FinalTPLab5DB2.exception.ResidenceNotExistsException;
import net.pelozo.FinalTPLab5DB2.model.Intake;
import net.pelozo.FinalTPLab5DB2.model.Measurement;
import net.pelozo.FinalTPLab5DB2.model.Meter;
import net.pelozo.FinalTPLab5DB2.model.Residence;
import net.pelozo.FinalTPLab5DB2.model.dto.MeasurementDto;
import net.pelozo.FinalTPLab5DB2.model.dto.MeasurementsDto;
import net.pelozo.FinalTPLab5DB2.repository.MeasurementRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Optional;

@Service
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final MeterService meterService;
    private final ResidenceService residenceService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, MeterService meterService, ResidenceService residenceService, ModelMapper modelMapper) {
        this.measurementRepository = measurementRepository;
        this.meterService = meterService;
        this.residenceService = residenceService;
        this.modelMapper = modelMapper;
    }

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




    public Optional<Intake> getIntakeByRangeOfDates(long id, LocalDateTime from, LocalDateTime to) throws NonExistentResourceException {

        Optional<Intake> intake = Optional.of(new Intake());

        LinkedList<Measurement> measurements = measurementRepository.findListOfIntakeByRangeOfDates(id,from,to);

        if(!measurements.isEmpty()){
            measurements.forEach(o -> intake.get()
                    .setKwhPrice(intake.get().getKwhPrice() + o.getKwhPrice()));

            intake.get().setKwhValue(measurements.getLast().getKwhValue() - measurements.getFirst().getKwhValue());

        } else{
            throw new NonExistentResourceException();
        }


        return intake;
    }

    public Page<MeasurementsDto> getMeasurementsByDateRange(long id, LocalDateTime from, LocalDateTime to, Pageable pageable) {

        return measurementRepository.findMeasurementsByRangeOfDate(id,from,to,pageable).map(o -> modelMapper.map(o, MeasurementsDto.class));
    }

    public Page<MeasurementsDto> getMeasurementsByResidenceAndRangeOfDate(long residenceId, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return measurementRepository.findByResidenceAndRangeOfDate(residenceId,from,to,pageable).map(o -> modelMapper.map(o, MeasurementsDto.class));
    }
}
