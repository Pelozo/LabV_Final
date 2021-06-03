package net.pelozo.FinalTPLab5DB2.utils;

import org.springframework.dao.DataIntegrityViolationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Misc {


    public static String parseDataConstraintEx(DataIntegrityViolationException ex){
        Pattern pattern = Pattern.compile(".*constraint.+FOREIGN KEY\\((.+)\\) REFERENCES", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(ex.getMessage());
        if(matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }

    }
}
