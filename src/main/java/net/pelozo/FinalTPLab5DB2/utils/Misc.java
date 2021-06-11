package net.pelozo.FinalTPLab5DB2.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.pelozo.FinalTPLab5DB2.model.dto.UserDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static net.pelozo.FinalTPLab5DB2.utils.Constants.JWT_SECRET;

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
