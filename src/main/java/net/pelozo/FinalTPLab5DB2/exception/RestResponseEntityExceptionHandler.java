package net.pelozo.FinalTPLab5DB2.exception;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request){
        List<String> errors = new ArrayList<>();
        for(ConstraintViolation violation : ex.getConstraintViolations()){
            errors.add(violation.getMessage());
        }
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getHttpstatus());
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(DataIntegrityViolationException ex, WebRequest request){
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getHttpstatus());
    }

    @ExceptionHandler({ClientExistsException.class})
    public ResponseEntity<Object> handleClientExistsException(ClientExistsException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getLocalizedMessage());
    }

    @ExceptionHandler(ClientNotExistsException.class)
    public ResponseEntity<String> clientNotExistsHandler(ClientNotExistsException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This client does not exists!");
    }

    @ExceptionHandler(ResidenceNotExistsException.class)
    public ResponseEntity<String> residenceNotExistsHandler(ResidenceNotExistsException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This residence does not exists!");
    }

    @ExceptionHandler(MeterNotExistsException.class)
    public ResponseEntity<String> meterNotExistsHandler(MeterNotExistsException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This meter does not exists!");
    }

}
