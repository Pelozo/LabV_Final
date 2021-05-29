package net.pelozo.FinalTPLab5DB2.controller;

import net.pelozo.FinalTPLab5DB2.dto.MeasurementsDto;
import net.pelozo.FinalTPLab5DB2.exception.MeterNotExistsException;
import net.pelozo.FinalTPLab5DB2.exception.ResidenceNotExistsException;
import net.pelozo.FinalTPLab5DB2.model.Measurement;
import net.pelozo.FinalTPLab5DB2.model.dto.MeasurementDto;
import net.pelozo.FinalTPLab5DB2.service.MeasurementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    @Autowired
    private MeasurementService measurementService;

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody MeasurementDto measurement) throws ResidenceNotExistsException, MeterNotExistsException {
        Measurement m = measurementService.add(measurement);
        return  ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(m.getId())
                .toUri())
                    .build();
    }

    @GetMapping
    public ResponseEntity<Page<MeasurementsDto>> getAllMeasurements(Pageable pageable){
        Page<MeasurementsDto> measurements = measurementService.getAll(pageable)
                .map(m -> new ModelMapper().map(m,MeasurementsDto.class));

        return ResponseEntity.status(measurements.isEmpty()? HttpStatus.NO_CONTENT: HttpStatus.OK).body(measurements);
    }
}
