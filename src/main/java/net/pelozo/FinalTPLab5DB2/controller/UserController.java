package net.pelozo.FinalTPLab5DB2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import net.pelozo.FinalTPLab5DB2.exception.InvalidCombinationUserPassword;
import net.pelozo.FinalTPLab5DB2.model.User;
import net.pelozo.FinalTPLab5DB2.model.dto.LoginRequestDto;
import net.pelozo.FinalTPLab5DB2.model.dto.LoginResponseDto;
import net.pelozo.FinalTPLab5DB2.model.dto.UserDto;
import net.pelozo.FinalTPLab5DB2.service.BackofficeService;
import net.pelozo.FinalTPLab5DB2.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static net.pelozo.FinalTPLab5DB2.utils.Constants.JWT_SECRET;

@Slf4j
@RestController
@RequestMapping(value = "/")
public class UserController {

    private ClientService clientService;
    private BackofficeService backofficeService;
    private ObjectMapper objectMapper;
    private ModelMapper modelMapper;

    @Autowired
    public UserController(ObjectMapper objectMapper, ModelMapper modelMapper, ClientService clientService, BackofficeService backoffice) {
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
        this.clientService = clientService;
        this.backofficeService = backoffice;
    }

    @PostMapping(value = "login")
    public ResponseEntity<LoginResponseDto> clientLogin(@RequestBody LoginRequestDto loginRequestDto) throws InvalidCombinationUserPassword {
        UserDto user = clientService.login(loginRequestDto.getUsername(), loginRequestDto.getPassword());

        return ResponseEntity.ok(LoginResponseDto.builder()
                .token(this.generateToken(user, User.TYPE.CLIENT.name()))
                .build());
    }

    @PostMapping(value = "backoffice/login")
    public ResponseEntity<LoginResponseDto> adminLogin(@RequestBody LoginRequestDto loginRequestDto) throws InvalidCombinationUserPassword {
        UserDto user = backofficeService.login(loginRequestDto.getUsername(), loginRequestDto.getPassword());
        return ResponseEntity
                .ok(LoginResponseDto.builder()
                        .token(this.generateToken(user, User.TYPE.BACKOFFICE.name()))
                        .build());
    }

    private String generateToken(UserDto userDto, String authority) {
        try {
            List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
            String token = Jwts
                    .builder()
                    .setId("JWT")
                    .setSubject(userDto.getUsername())
                    .claim("user", objectMapper.writeValueAsString(userDto))
                    .claim("authorities",grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 10)) //10 dias
                    .signWith(SignatureAlgorithm.HS512, JWT_SECRET.getBytes())
                    .compact();
            return  token;
        } catch(Exception e) {
            return "dummy";
        }
    }

}
