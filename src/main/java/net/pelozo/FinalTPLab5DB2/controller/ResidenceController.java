package net.pelozo.FinalTPLab5DB2.controller;

import net.pelozo.FinalTPLab5DB2.exception.ClientNotExistsException;
import net.pelozo.FinalTPLab5DB2.exception.IdViolationException;
import net.pelozo.FinalTPLab5DB2.exception.NonExistentResourceException;
import net.pelozo.FinalTPLab5DB2.model.Residence;
import net.pelozo.FinalTPLab5DB2.model.dto.MeasurementsDto;
import net.pelozo.FinalTPLab5DB2.model.dto.NewResidenceDto;
import net.pelozo.FinalTPLab5DB2.model.dto.ResidenceDto;
import net.pelozo.FinalTPLab5DB2.service.ResidenceService;
import net.pelozo.FinalTPLab5DB2.utils.EntityURLBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static net.pelozo.FinalTPLab5DB2.utils.MyResponse.response;

@RestController
@RequestMapping("residences")
public class ResidenceController {


    ResidenceService residenceService;
    ModelMapper modelMapper;

    @Autowired
    public ResidenceController(ResidenceService residenceService, ModelMapper modelMapper) {
        this.residenceService = residenceService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public ResponseEntity<List<ResidenceDto>> getAll(Pageable pageable){
        Page<ResidenceDto> residences =  residenceService.getAll(pageable);
        return response(residences);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ResidenceDto>> getResidenceByClient(@PathVariable Long clientId, Pageable pageable) throws ClientNotExistsException {
        Page<ResidenceDto> residences = residenceService.getByClient(clientId, pageable).map(o -> modelMapper.map(o, ResidenceDto.class));
        return response(residences);
    }

    //3) Alta, baja y modificación de domicilios y medidores
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) throws NonExistentResourceException {
        residenceService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

    //3) Alta, baja y modificación de domicilios y medidores
    @PutMapping("/{id}")
    public ResponseEntity updateResidence(@PathVariable Long id, @RequestBody NewResidenceDto residence) throws NonExistentResourceException, IdViolationException {
        residenceService.update(id, residence);
        return ResponseEntity.accepted().build();
    }



}
