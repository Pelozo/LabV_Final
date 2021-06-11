package net.pelozo.FinalTPLab5DB2.controller;

import net.pelozo.FinalTPLab5DB2.exception.ClientNotExistsException;
import net.pelozo.FinalTPLab5DB2.exception.IdViolationException;
import net.pelozo.FinalTPLab5DB2.exception.NonExistentResourceException;
import net.pelozo.FinalTPLab5DB2.model.Residence;
import net.pelozo.FinalTPLab5DB2.model.dto.ResidenceDto;
import net.pelozo.FinalTPLab5DB2.service.ResidenceService;
import net.pelozo.FinalTPLab5DB2.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static net.pelozo.FinalTPLab5DB2.utils.MyResponse.response;

@RestController
@RequestMapping("residences")
public class ResidenceController {

    @Autowired
    ResidenceService residenceService;

    @GetMapping("/")
    public ResponseEntity<List<ResidenceDto>> getAll(Pageable pageable){
        Page<ResidenceDto> residences =  residenceService.getAll(pageable);
        return response(residences);
    }

    @PostMapping("/clients/{clientId}/residencies")
    public ResponseEntity<String> addResidence(@PathVariable Long clientId, @RequestBody Residence newResidence) throws ClientNotExistsException {
        Residence residence = residenceService.add(clientId, newResidence);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(EntityURLBuilder.buildURL("residences",residence.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .body("");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) throws NonExistentResourceException {
        residenceService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateResidence(@PathVariable Long id, @RequestBody Residence residence) throws NonExistentResourceException, IdViolationException {
        residenceService.update(id, residence);
        return ResponseEntity.accepted().build();
    }


}
