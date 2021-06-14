package net.pelozo.FinalTPLab5DB2.model.TestDeleteLater;

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
@RequestMapping("/models")
public class MeterModelController {


    MeterModelService service;

    @Autowired
    public MeterModelController(MeterModelService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<MeterModel2>> getAll(Pageable pageable){
        Page<MeterModel2> tariffs = service.getAll(pageable);
        return response(tariffs);
    }

    @PreAuthorize(value= "hasAuthority('BACKOFFICE')")
    @PostMapping
    public ResponseEntity add(@RequestBody MeterModel2 newMeter){
        MeterModel2 meter =  service.add(newMeter);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(EntityURLBuilder.buildURL("models", meter.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .body("");
    }

}
