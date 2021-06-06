package net.pelozo.FinalTPLab5DB2.exception;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import static net.pelozo.FinalTPLab5DB2.utils.Misc.parseDataConstraintEx;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler({ConstraintViolationException.class})
//    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request){
////        List<String> errors = new ArrayList<>();
////        for(ConstraintViolation violation : ex.getConstraintViolations()){
////            errors.add(violation.getMessage());
////        }
////        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
////        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getHttpstatus());
//
//        return  ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiError(1,ex.getMessage()));
//    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(DataIntegrityViolationException ex, WebRequest request){
//        List<String> errors = new ArrayList<>();
//        errors.add(ex.getMessage());
//        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getLocalizedMessage(), errors);
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


}
