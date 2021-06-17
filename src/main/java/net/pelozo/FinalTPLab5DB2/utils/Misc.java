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

    private Misc() {}

    public static String parseDataConstraintEx(DataIntegrityViolationException ex){
        //Pattern pattern = Pattern.compile(".+?\\((.+?)\\)", Pattern.CASE_INSENSITIVE);
        Pattern pattern = Pattern.compile("(?:UNQ|FK|PK)_(.+?)[_\\]].*?\\((.+?)\\)", Pattern.CASE_INSENSITIVE);
        //Pattern pattern = Pattern.compile("(?:UNQ|FK|PK)_(.+?)[_\\]].*?", Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(ex.getMessage());

        ex.printStackTrace();
        if(matcher.find() && matcher.groupCount() == 2) {
            return "Constraint error with " + matcher.group(2).toLowerCase().replace("_", " ") + " in " + matcher.group(1).toLowerCase();
        } else {
            return "Constraint error" ;//+ ex.getMessage();
        }
    }

}
