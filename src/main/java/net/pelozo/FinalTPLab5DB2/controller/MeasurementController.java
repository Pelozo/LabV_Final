package net.pelozo.FinalTPLab5DB2.controller;

import net.pelozo.FinalTPLab5DB2.dto.MeasurementsDto;
import net.pelozo.FinalTPLab5DB2.model.Measurement;
import net.pelozo.FinalTPLab5DB2.model.dto.MeasurementDto;
import net.pelozo.FinalTPLab5DB2.service.MeasurementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurement")
public class MeasurementController {

    @Autowired
    private MeasurementService measurementService;

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody MeasurementDto measurement){
        Measurement m = measurementService.add(measurement);
        return  ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(m.getId())
                .toUri())
                    .build();
    }

    @GetMapping
    public ResponseEntity<List<MeasurementsDto>> getAllMeasurements(){
        List<MeasurementsDto> measurements = measurementService.getAll()
                .stream()
                .map(o -> new ModelMapper().map(o,MeasurementsDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.status(measurements.isEmpty()? HttpStatus.NO_CONTENT: HttpStatus.OK).body(measurements);
    }
}
