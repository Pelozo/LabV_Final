package net.pelozo.FinalTPLab5DB2.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
public class ApiError {
    private Integer errorCode;
    private String errorMessage;

    public ApiError(Integer code, String message) {
        errorCode = code;
        errorMessage = message;
    }
}
