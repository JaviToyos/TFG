package com.tfg.gestorcuentas.controller;

import com.tfg.gestorcuentas.service.login.LoginService;
import com.tfg.gestorcuentas.service.login.model.LoggedUser;
import com.tfg.gestorcuentas.service.login.model.Login;
import com.tfg.gestorcuentas.service.login.model.RecuperarPass;
import com.tfg.gestorcuentas.utils.Messages;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {
    private static final String USERNAME = "javi";
    private static final String PASSWD = "javi";
    public static final String MAIL = "javiertoyos1@gmail.com";

    @Mock
    private LoginService loginService;

    private LoginController loginController;

    @Before
    public void setUp() {
        loginController = new LoginController(loginService);
    }

    @Test
    public void testLogin()  {
        Login login = Login.builder()
                .withUserName(USERNAME)
                .withPasswd(PASSWD)
                .build();
        LoggedUser loggedUserExpected = LoggedUser.builder()
                .withUser(USERNAME)
                .withToken( "Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoiamF2aSIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2ODMxODI5MDgsImV4cCI6MTY4MzE4ODkwOH0.whhYJ053emdoqBfd7ukLqoHv7402yGlcT9MD0icwek4IxMWYp_TuetI9mlNrcqWM3wOiM5rDfO59jIqddcwCNA")
                .build();

        given(loginService.login(login)).willReturn(loggedUserExpected);

        ResponseEntity<?> loggedUser = loginController.login(login);

        verify(loginService).login(login);
        Assert.assertEquals(HttpStatus.OK, loggedUser.getStatusCode());
        Assert.assertEquals(loggedUserExpected, loggedUser.getBody());
    }

    @Test
    public void testLoginPassIncorrecta(){
        Login login = Login.builder()
                .withUserName(USERNAME)
                .withPasswd("newPass")
                .build();
        LoggedUser loggedUserExpected = LoggedUser.builder()
                .build();

        given(loginService.login(login)).willReturn(loggedUserExpected);

        ResponseEntity<?> loggedUser = loginController.login(login);

        verify(loginService).login(login);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, loggedUser.getStatusCode());
    }

    @Test
    public void testLoginIncorrecto(){
        Login login = Login.builder()
                .withUserName(USERNAME)
                .build();

        given(loginService.login(login)).willThrow(IllegalArgumentException.class);

        ResponseEntity<?> loggedUser = loginController.login(login);

        verify(loginService).login(login);

        Assert.assertEquals(HttpStatus.UNAUTHORIZED, loggedUser.getStatusCode());
    }

    @Test
    public void testPassBackUp()  {
        RecuperarPass recuperarPass = RecuperarPass.builder()
                .withUsername(USERNAME)
                .withEmail(MAIL)
                .build();

        given(loginService.recuperarPasswdUsuario(recuperarPass)).willReturn(Messages.PASSWORD_MODIFIED.getMessage());

        ResponseEntity<?> passBackup = loginController.passBackup(recuperarPass);

        verify(loginService).recuperarPasswdUsuario(recuperarPass);

        Assert.assertEquals(HttpStatus.OK, passBackup.getStatusCode());
        Assert.assertEquals(Messages.MAIL_ENVIADO_OK.getMessage(), passBackup.getBody());
    }

    @Test
    public void testRecuperarPassIllegalArgument(){
        RecuperarPass recuperarPass = RecuperarPass.builder()
                .withUsername("pepe")
                .withEmail(MAIL)
                .build();

        given(loginService.recuperarPasswdUsuario(recuperarPass)).willThrow(IllegalArgumentException.class);

        ResponseEntity<?> passBackup = loginController.passBackup(recuperarPass);

        verify(loginService).recuperarPasswdUsuario(recuperarPass);

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, passBackup.getStatusCode());
    }

    @Test
    public void testRecuperarPassIllegalState(){
        RecuperarPass recuperarPass = RecuperarPass.builder()
                .withUsername("pepe")
                .withEmail(MAIL)
                .build();

        given(loginService.recuperarPasswdUsuario(recuperarPass)).willThrow(IllegalStateException.class);

        ResponseEntity<?> passBackup = loginController.passBackup(recuperarPass);

        verify(loginService).recuperarPasswdUsuario(recuperarPass);

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, passBackup.getStatusCode());
    }

    @Test
    public void testRecuperarPassBadRequest(){
        RecuperarPass recuperarPass = RecuperarPass.builder()
                .withUsername("pepe")
                .withEmail(MAIL)
                .build();

        given(loginService.recuperarPasswdUsuario(recuperarPass)).willReturn(StringUtils.EMPTY);

        ResponseEntity<?> passBackup = loginController.passBackup(recuperarPass);

        verify(loginService).recuperarPasswdUsuario(recuperarPass);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, passBackup.getStatusCode());
    }
}