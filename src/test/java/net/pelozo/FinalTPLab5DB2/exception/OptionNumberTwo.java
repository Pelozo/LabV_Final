package net.pelozo.FinalTPLab5DB2.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class OptionNumberTwo {

    RestResponseEntityExceptionHandler handler;
    WebRequest webRequest;

    @BeforeEach
    public void setUp() {
        webRequest = mock(WebRequest.class);
        handler = new RestResponseEntityExceptionHandler();
    }

    @Test
    public void idkDude(){
        ClientNotExistsException e = new ClientNotExistsException();
        ResponseEntity<ApiError> response = handler.clientNotExistsHandler(e, webRequest);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void idkDude2(){

        //throw new InvalidCombinationUserPassword();

        InvalidCombinationUserPassword e = new InvalidCombinationUserPassword();
        ResponseEntity<ApiError> response = handler.InvalidCombinationHandler(e, webRequest);
        assertEquals(HttpStatus.UNAUTHORIZED,response.getStatusCode());
        assertEquals(130, response.getBody().getErrorCode());

    }
}
