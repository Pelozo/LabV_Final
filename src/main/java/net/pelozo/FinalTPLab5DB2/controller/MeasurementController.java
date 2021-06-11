package net.pelozo.FinalTPLab5DB2.controller;

import net.pelozo.FinalTPLab5DB2.model.dto.MeasurementsDto;
import net.pelozo.FinalTPLab5DB2.exception.MeterNotExistsException;
import net.pelozo.FinalTPLab5DB2.exception.ResidenceNotExistsException;
import net.pelozo.FinalTPLab5DB2.model.Measurement;
import net.pelozo.FinalTPLab5DB2.model.dto.MeasurementDto;
import net.pelozo.FinalTPLab5DB2.service.MeasurementService;
import net.pelozo.FinalTPLab5DB2.utils.EntityURLBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static net.pelozo.FinalTPLab5DB2.utils.MyResponse.response;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {


    private MeasurementService measurementService;

    @Autowired
    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody MeasurementDto measurement) throws ResidenceNotExistsException, MeterNotExistsException {
        Measurement m = measurementService.add(measurement);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(EntityURLBuilder.buildURL("measurements",m.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .body("");

    }

    @GetMapping
    public ResponseEntity<List<MeasurementsDto>> getAllMeasurements(Pageable pageable){
        Page<MeasurementsDto> measurements = measurementService.getAll(pageable)
                .map(m -> new ModelMapper().map(m,MeasurementsDto.class));

        return response(measurements);
    }


    public Page<MeasurementsDto> getMeasurementsByResidenceAndRangeOfDates(long residenceId, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return measurementService.getMeasurementsByResidenceAndRangeOfDate(residenceId,from,to,pageable);
    }
}
