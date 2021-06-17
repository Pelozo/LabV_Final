package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.exception.ClientNotExistsException;
import net.pelozo.FinalTPLab5DB2.exception.InvalidResourceIdException;
import net.pelozo.FinalTPLab5DB2.exception.NonExistentResourceException;
import net.pelozo.FinalTPLab5DB2.model.Client;
import net.pelozo.FinalTPLab5DB2.model.Meter;
import net.pelozo.FinalTPLab5DB2.model.Residence;
import net.pelozo.FinalTPLab5DB2.model.Tariff;
import net.pelozo.FinalTPLab5DB2.model.dto.MeterDto;
import net.pelozo.FinalTPLab5DB2.model.dto.NewResidenceDto;
import net.pelozo.FinalTPLab5DB2.model.dto.ResidenceDto;
import net.pelozo.FinalTPLab5DB2.repository.ClientRepository;
import net.pelozo.FinalTPLab5DB2.repository.MeterRepository;
import net.pelozo.FinalTPLab5DB2.repository.ResidenceRepository;
import net.pelozo.FinalTPLab5DB2.repository.TariffRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResidenceService {


    private ResidenceRepository residenceRepository;
    private ModelMapper modelMapper;
    private ClientService clientService;
    private TariffRepository tariffRepository;
    private MeterRepository meterRepository;
    private ClientRepository clientRepository;


    @Autowired
    public ResidenceService(ResidenceRepository residenceRepository, ModelMapper modelMapper, ClientService clientService, TariffRepository tariffRepository, MeterRepository meterRepository, ClientRepository clientRepository) {
        this.residenceRepository = residenceRepository;
        this.modelMapper = modelMapper;
        this.clientService = clientService;
        this.tariffRepository = tariffRepository;
        this.meterRepository = meterRepository;
        this.clientRepository = clientRepository;
    }

    public Optional<Residence> getByMeter(Meter meter){
        return residenceRepository.findByMeterId(meter.getId());
    }

    public Page<ResidenceDto> getAll(Pageable pageable) {
        return residenceRepository.findAll(pageable).map(residence -> modelMapper.map(residence, ResidenceDto.class));
    }

    public Residence add(Long clientId, Residence residence) throws ClientNotExistsException, InvalidResourceIdException {
        Client client = clientService.getById(clientId);
        Optional<Tariff> t = tariffRepository.findById(residence.getTariff().getId());
        Optional<Meter> m = meterRepository.findById(residence.getMeter().getId());

        if (t.isEmpty()) {
            throw new InvalidResourceIdException("Tariff");
        }
        if (m.isEmpty()) {
            throw new InvalidResourceIdException("Meter");
        }



        residence.setTariff(t.get());
        residence.setMeter(m.get());
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

    public Residence update(Long id, NewResidenceDto residence) throws NonExistentResourceException, InvalidResourceIdException {
        Optional<Residence> newResidence = residenceRepository.findById(id);

        if(newResidence.isPresent()){
            Optional<Tariff> t = tariffRepository.findById(residence.getTariff().getId());
            Optional<Meter> m = meterRepository.findById(residence.getMeter().getId());

            if (t.isEmpty()) {
                throw new InvalidResourceIdException("Tariff");
            }
            if (m.isEmpty()) {
                throw new InvalidResourceIdException("Meter");
            }
            newResidence.get().setTariff(t.get());
            newResidence.get().setMeter(m.get());
            newResidence.get().setAddress(residence.getAddress());
            return residenceRepository.save(newResidence.get());
        }else{
            throw new NonExistentResourceException();
        }
    }

    public Page<Residence> getByClient(Long clientId, Pageable pageable) throws ClientNotExistsException {
        Client client = clientService.getById(clientId);
        if(client != null){
            return residenceRepository.findAllByClientId(clientId, pageable);
        }else{
            throw new ClientNotExistsException();
        }
    }
}
