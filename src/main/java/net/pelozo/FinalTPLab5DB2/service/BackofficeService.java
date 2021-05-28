package net.pelozo.FinalTPLab5DB2.service;

import net.pelozo.FinalTPLab5DB2.model.Backoffice;
import net.pelozo.FinalTPLab5DB2.model.Client;
import net.pelozo.FinalTPLab5DB2.repository.BackofficeRepository;
import net.pelozo.FinalTPLab5DB2.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BackofficeService {

    @Autowired
    BackofficeRepository backofficeRepository;

    public Backoffice login(String username, String password) {
        return backofficeRepository.findByUsernameAndPassword(username, password);
    }
}
