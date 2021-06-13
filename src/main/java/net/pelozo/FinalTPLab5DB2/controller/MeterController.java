package net.pelozo.FinalTPLab5DB2.controller;

import net.pelozo.FinalTPLab5DB2.exception.IdViolationException;
import net.pelozo.FinalTPLab5DB2.exception.NonExistentResourceException;
import net.pelozo.FinalTPLab5DB2.model.Meter;
import net.pelozo.FinalTPLab5DB2.model.Tariff;
import net.pelozo.FinalTPLab5DB2.model.dto.MeterDto;
import net.pelozo.FinalTPLab5DB2.service.MeterService;
import net.pelozo.FinalTPLab5DB2.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static net.pelozo.FinalTPLab5DB2.utils.MyResponse.response;

@RestController
@RequestMapping("/meters")
public class MeterController {

    private MeterService meterService;

    @Autowired
    public MeterController(MeterService meterService) {
        this.meterService = meterService;
    }

    @GetMapping
    public ResponseEntity<List<MeterDto>> getAll(Pageable pageable){
        Page<MeterDto> meters = meterService.getAll(pageable);
        return response(meters);
    }

    //3) Alta, baja y modificación de domicilios y medidores
    @PreAuthorize(value= "hasAuthority('BACKOFFICE')")
    @PostMapping
    public ResponseEntity add(@RequestBody Meter newMeter){
        MeterDto meter =  meterService.add(newMeter);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(EntityURLBuilder.buildURL("meters", meter.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .body("");
    }

    @PreAuthorize(value= "hasAuthority('BACKOFFICE')")
    @GetMapping("/{id}")
    public ResponseEntity<MeterDto> getById(@PathVariable Long id) throws NonExistentResourceException {
        MeterDto meter = meterService.getById(id);
        return ResponseEntity.ok(meter);
    }

    //3) Alta, baja y modificación de domicilios y medidores
    @PreAuthorize(value= "hasAuthority('BACKOFFICE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) throws NonExistentResourceException{
        meterService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

    //3) Alta, baja y modificación de domicilios y medidores
    @PreAuthorize(value= "hasAuthority('BACKOFFICE')")
    @PutMapping("/{id}")
    public ResponseEntity updateMeter(@PathVariable Long id,
                                       @RequestBody Meter meter) throws NonExistentResourceException, IdViolationException {
        meterService.update(id, meter);
        return ResponseEntity.accepted().build();
    }

}
