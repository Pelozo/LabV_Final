package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.exception.ClientNotExistsException;
import net.pelozo.FinalTPLab5DB2.exception.NonExistentResourceException;
import net.pelozo.FinalTPLab5DB2.model.Client;
import net.pelozo.FinalTPLab5DB2.model.Meter;
import net.pelozo.FinalTPLab5DB2.model.Residence;
import net.pelozo.FinalTPLab5DB2.model.dto.ResidenceDto;
import net.pelozo.FinalTPLab5DB2.repository.ResidenceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResidenceService {


    private ResidenceRepository residenceRepository;
    private ModelMapper modelMapper;
    private ClientService clientService;

    @Autowired
    public ResidenceService(ResidenceRepository residenceRepository, ModelMapper modelMapper, ClientService clientService) {
        this.residenceRepository = residenceRepository;
        this.modelMapper = modelMapper;
        this.clientService = clientService;
    }

    public Optional<Residence> getByMeter(Meter meter){
        return residenceRepository.findByMeterId(meter.getId());
    }

    public Page<ResidenceDto> getAll(Pageable pageable) {
        return residenceRepository.findAll(pageable).map(residence -> modelMapper.map(residence, ResidenceDto.class));
    }

    public Residence add(Long clientId, Residence residence) throws ClientNotExistsException {
        Client client = clientService.getById(clientId);
        residence.setClient(client);
        return residenceRepository.save(residence);
    }

    public void deleteById(Long id) throws NonExistentResourceException {
        Optional<Residence> residence = residenceRepository.findById(id);
        if(residence.isPresent()){
            residenceRepository.deleteById(id);
        }else{
            throw new NonExistentResourceException();
        }
    }

    public Residence update(Long id, Residence residence) throws NonExistentResourceException {
        Optional<Residence> newResidence = residenceRepository.findById(id);
        if(newResidence.isPresent()) {
            return residenceRepository.save(residence);
        }else{
            throw new NonExistentResourceException();
        }
    }

}
