package net.pelozo.FinalTPLab5DB2.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class ClientExistsException extends DataIntegrityViolationException {

    public static final Integer errorCode = 120;

    public ClientExistsException() {
        super("This client already exists!");
    }

}
