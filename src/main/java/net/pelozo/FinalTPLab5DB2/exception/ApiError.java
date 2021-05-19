package net.pelozo.FinalTPLab5DB2.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;


@AllArgsConstructor
@Data
@Builder
public class ApiError {
    private HttpStatus Httpstatus;
    private String message;
    private List<String> errors;
}
