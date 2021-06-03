package net.pelozo.FinalTPLab5DB2.exception;

import lombok.Data;

@Data
public class ApiException{

    private Integer errorCode;
    private String errorMessage;

    public ApiException(Integer code, String message) {
        errorCode = code;
        errorMessage = message;
    }
}
