package net.pelozo.FinalTPLab5DB2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.pelozo.FinalTPLab5DB2.exception.InvalidCombinationUserPassword;
import net.pelozo.FinalTPLab5DB2.model.dto.LoginResponseDto;
import net.pelozo.FinalTPLab5DB2.service.BackofficeService;
import net.pelozo.FinalTPLab5DB2.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static net.pelozo.FinalTPLab5DB2.utils.TestUtils.aLoginRequestDto;
import static net.pelozo.FinalTPLab5DB2.utils.TestUtils.aUserDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
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

    @Test
    public void clientLogin_ThrowsInvalidCombinationUserPasswordTest() throws InvalidCombinationUserPassword {
        //given
        when(clientService.login(anyString(), anyString())).thenThrow(new InvalidCombinationUserPassword());
        //then
        assertThrows(InvalidCombinationUserPassword.class, () -> {
            userController.clientLogin(aLoginRequestDto());
        });
    }


    @Test
    public void backofficeLoginOkTest(){
        try {
            when(backofficeService.login(anyString(), anyString())).thenReturn(aUserDto());

            ResponseEntity<LoginResponseDto> response = userController.adminLogin(aLoginRequestDto());

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody().getToken());

        } catch (InvalidCombinationUserPassword invalidCombinationUserPassword) {
            fail();
        }
    }

    @Test
    public void adminLogin_ThrowsInvalidCombinationUserPasswordTest() throws InvalidCombinationUserPassword {
        //given
        when(backofficeService.login(anyString(), anyString())).thenThrow(new InvalidCombinationUserPassword());
        //then
        assertThrows(InvalidCombinationUserPassword.class, () -> {
            userController.adminLogin(aLoginRequestDto());
        });
    }





}
