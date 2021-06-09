package net.pelozo.FinalTPLab5DB2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.pelozo.FinalTPLab5DB2.exception.InvalidCombinationUserPassword;
import net.pelozo.FinalTPLab5DB2.model.User;
import net.pelozo.FinalTPLab5DB2.model.dto.LoginResponseDto;
import net.pelozo.FinalTPLab5DB2.model.dto.UserDto;
import net.pelozo.FinalTPLab5DB2.service.BackofficeService;
import net.pelozo.FinalTPLab5DB2.service.ClientService;
import net.pelozo.FinalTPLab5DB2.service.TariffService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static net.pelozo.FinalTPLab5DB2.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {


    ClientService clientService;
    BackofficeService backofficeService;
    ObjectMapper objectMapper;
    ModelMapper modelMapper;

    UserController userController;

    @BeforeEach
    public void setUp(){
        clientService = mock(ClientService.class);
        backofficeService = mock(BackofficeService.class);
        objectMapper = mock(ObjectMapper.class);
        modelMapper = mock(ModelMapper.class);
        userController = new UserController(objectMapper,modelMapper,clientService,backofficeService);
    }


    @Test
    public void clientloginOkTest(){

        try {
            when(clientService.login(anyString(), anyString())).thenReturn(aUserDto());

            ResponseEntity<LoginResponseDto> response = userController.clientLogin(aLoginRequestDto());

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody().getToken());


        } catch (InvalidCombinationUserPassword invalidCombinationUserPassword) {
            fail();
        }



    }

}
