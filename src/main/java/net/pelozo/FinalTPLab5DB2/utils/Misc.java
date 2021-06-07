package net.pelozo.FinalTPLab5DB2.utils;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Misc {

    public static String parseDataConstraintEx(DataIntegrityViolationException ex){
        Pattern pattern = Pattern.compile(".+?\\((.+?)\\)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(ex.getMessage());
        if(matcher.find()) {
            return "Constraint error with "  + matcher.group(1).toLowerCase();
        } else {
            return "Constraint error";
        }
    }
}
