package net.pelozo.FinalTPLab5DB2.service;


import net.pelozo.FinalTPLab5DB2.exception.ClientExistsException;
import net.pelozo.FinalTPLab5DB2.exception.IdViolationException;
import net.pelozo.FinalTPLab5DB2.exception.NonExistentResourceException;
import net.pelozo.FinalTPLab5DB2.model.BaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public abstract class BaseService<T extends BaseModel> {

    private final JpaRepository<T, Long> repository;

    @Autowired
    public BaseService(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    public T add(T object){
        try{
            return repository.save(object);
        }catch (DataIntegrityViolationException ex){
            throw new ClientExistsException();
        }
    }

    public void deleteById(Long id) throws NonExistentResourceException{
        Optional<T> object = repository.findById(id);
        if(object.isPresent()){
            repository.deleteById(object.get().getId());
        }else{
            throw new NonExistentResourceException();
        }
    }

    public T getById(Long id) throws NonExistentResourceException{
        Optional<T> object = repository.findById(id);
        if(object.isPresent()){
            return object.get();
        }else{
            throw new NonExistentResourceException();
        }
    }

    public Page<T> getAll(Pageable pageable){
        return repository.findAll(pageable);
    }


    public void update(Long id, T _object) throws NonExistentResourceException, IdViolationException {
        Optional<T> object = repository.findById(id);
        if(object.isPresent()){
            if(!object.get().getId().equals(_object.getId())){
                throw new IdViolationException();
            }else{
                _object.setId(object.get().getId());
                repository.save(_object);
            }
        }else{
            throw new NonExistentResourceException();
        }
    }
}
