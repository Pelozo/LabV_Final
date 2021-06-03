package net.pelozo.FinalTPLab5DB2.utils;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class MyResponse {

    public static ResponseEntity.BodyBuilder response(Page page){
        return ResponseEntity.status(page.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK)
                .header("X-Total-Pages", String.valueOf(page.getTotalPages()))
                .header("X-Total-Content",String.valueOf(page.getTotalElements()));
    }
}
