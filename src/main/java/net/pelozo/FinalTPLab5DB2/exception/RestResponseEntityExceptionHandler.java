package net.pelozo.FinalTPLab5DB2.exception;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import javax.validation.ConstraintViolationException;

import static net.pelozo.FinalTPLab5DB2.utils.Misc.parseDataConstraintEx;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler({ConstraintViolationException.class})
//    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request){
//        List<String> errors = new ArrayList<>();
//        for(ConstraintViolation violation : ex.getConstraintViolations()){
//            errors.add(violation.getMessage());
//        }
//        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
//        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getHttpstatus());
//
//        return  ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiError(1,ex.getMessage()));
//    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request){
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(2, ex.getConstraintViolations().iterator().next().getMessage()));
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(DataIntegrityViolationException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(1,parseDataConstraintEx(ex)));
    }

    @ExceptionHandler({ClientExistsException.class})
    public ResponseEntity<Object> handleClientExistsException(ClientExistsException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiError(ClientExistsException.errorCode,ex.getMessage()));
    }

    @ExceptionHandler(ClientNotExistsException.class)
    public ResponseEntity<ApiError> clientNotExistsHandler(ClientNotExistsException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(ClientNotExistsException.errorCode,ex.getMessage()));
    }

    @ExceptionHandler(ResidenceNotExistsException.class)
    public ResponseEntity<ApiError> residenceNotExistsHandler(ResidenceNotExistsException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(ResidenceNotExistsException.errorCode,ex.getMessage()));
    }

    @ExceptionHandler(MeterNotExistsException.class)
    public ResponseEntity<ApiError> meterNotExistsHandler(MeterNotExistsException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(MeterNotExistsException.errorCode,ex.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> AccessDeniedViolation(AccessDeniedException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied!");
    }

    @ExceptionHandler(NonExistentResourceException.class)
    public ResponseEntity<ApiError> NonExistentResource(NonExistentResourceException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(NonExistentResourceException.errorCode, ex.getMessage()));
    }

    @ExceptionHandler(IdViolationException.class)
    public ResponseEntity<ApiError> NonExistentResource(IdViolationException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(IdViolationException.errorCode, ex.getMessage()));
    }

    @ExceptionHandler(InvalidCombinationUserPassword.class)
    public ResponseEntity<ApiError> InvalidCombinationHandler(InvalidCombinationUserPassword ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiError(IdViolationException.errorCode, ex.getMessage()));
    }

}
