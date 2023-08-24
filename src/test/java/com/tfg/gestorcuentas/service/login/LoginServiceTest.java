package com.tfg.gestorcuentas.service.login;

import com.tfg.gestorcuentas.data.entity.PersonaEntity;
import com.tfg.gestorcuentas.data.entity.UsuarioEntity;
import com.tfg.gestorcuentas.data.repository.IUsuarioJPARepository;
import com.tfg.gestorcuentas.mail.service.IMailService;
import com.tfg.gestorcuentas.service.login.model.GetJWTToken;
import com.tfg.gestorcuentas.service.login.model.LoggedUser;
import com.tfg.gestorcuentas.service.login.model.Login;
import com.tfg.gestorcuentas.service.login.model.RecuperarPass;
import com.tfg.gestorcuentas.utils.Constantes;
import com.tfg.gestorcuentas.utils.Messages;
import jakarta.mail.MessagingException;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {
    private static final String USERNAME = "javier";
    private static final String PASSWORD = "javier";
    private static final String EMAIL = "javiertoyos1@gmail.com";

    @Mock
    private IUsuarioJPARepository usuarioJPARepository;
    @Mock
    private IMailService iMailService;
    @Mock
    private GetJWTToken getJWTToken;
    private LoginService loginService;

    @Before
    public void setUp() throws Exception {
        loginService = new LoginService(usuarioJPARepository, iMailService, getJWTToken);
    }

    @Test
    public void testLogin() {
        Login login = Login.builder()
                .withUserName(USERNAME)
                .withPasswd(PASSWORD)
                .build();
        UsuarioEntity usuarioEntity = buildUsuarioEntity();
        String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoiamF2aSIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2ODMxODI5MDgsImV4cCI6MTY4MzE4ODkwOH0.whhYJ053emdoqBfd7ukLqoHv7402yGlcT9MD0icwek4IxMWYp_TuetI9mlNrcqWM3wOiM5rDfO59jIqddcwCNA";
        LoggedUser loggedUserExpected = LoggedUser.builder()
                .withUser(USERNAME)
                .withToken(token)
                .build();

        given(usuarioJPARepository.findByUsername(USERNAME)).willReturn(Optional.of(usuarioEntity));
        given(getJWTToken.getJWTToken(USERNAME)).willReturn(token);

        LoggedUser loggedUser = loginService.login(login);

        verify(usuarioJPARepository).findByUsername(USERNAME);
        verify(getJWTToken).getJWTToken(USERNAME);

        Assert.assertEquals(loggedUserExpected, loggedUser);
        Assert.assertNotNull(token);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testLoginUserEmpty() {
        Login login = Login.builder()
                .withPasswd(PASSWORD)
                .build();
        loginService.login(login);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testLoginPasswordEmpty() {
        Login login = Login.builder()
                .withUserName(USERNAME)

                .build();
        loginService.login(login);
    }

    @Test
    public void testLoginUsuarioNoActivo() {
        Login login = Login.builder()
                .withUserName(USERNAME)
                .withPasswd(PASSWORD)
                .build();
        UsuarioEntity usuarioEntity = buildUsuarioEntityNoActivo();
        LoggedUser loggedUserExpected = LoggedUser.builder().build();

        given(usuarioJPARepository.findByUsername(USERNAME)).willReturn(Optional.of(usuarioEntity));

        LoggedUser loggedUser = loginService.login(login);

        verify(usuarioJPARepository).findByUsername(USERNAME);

        Assert.assertEquals(loggedUserExpected, loggedUser);
    }

    @Test
    public void testLoginUsuarioPassNoCoincide() {
        Login login = Login.builder()
                .withUserName(USERNAME)
                .withPasswd("newPassword")
                .build();

        UsuarioEntity usuarioEntity = buildUsuarioEntityNoActivo();
        LoggedUser loggedUserExpected = LoggedUser.builder().build();

        given(usuarioJPARepository.findByUsername(USERNAME)).willReturn(Optional.of(usuarioEntity));

        LoggedUser loggedUser = loginService.login(login);

        verify(usuarioJPARepository).findByUsername(USERNAME);

        Assert.assertEquals(loggedUserExpected, loggedUser);
        Assert.assertNotEquals(PASSWORD, "newPassword");
    }

    @Test
    public void testRecuperarPass() throws MessagingException {
        RecuperarPass recuperarPass = RecuperarPass.builder()
                .withEmail(EMAIL)
                .withUsername(USERNAME)
                .build();

        UsuarioEntity usuario = Mockito.mock(UsuarioEntity.class);
        PersonaEntity persona = Mockito.mock(PersonaEntity.class);
        String passwdTemp = "12wsdsw";

        given(usuarioJPARepository.findByUsername(USERNAME)).willReturn(Optional.of(usuario));
        given(usuario.getUsername()).willReturn(USERNAME);
        given(persona.getEmail()).willReturn(EMAIL);
        given(usuario.getPersona()).willReturn(persona);

        usuario.setPassword((DigestUtils.md5Hex(passwdTemp)));

        String resultado = loginService.recuperarPasswdUsuario(recuperarPass);

        Mockito.verify(usuarioJPARepository).findByUsername(USERNAME);
        Mockito.verify(iMailService).enviarMail(any());
        Mockito.verify(usuarioJPARepository).save(usuario);

        Assert.assertEquals(Messages.PASSWORD_MODIFIED.getMessage(), resultado);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRecuperarPassWrongEmail() throws MessagingException {
        RecuperarPass recuperarPass = RecuperarPass.builder()
                .withEmail(EMAIL)
                .withUsername(USERNAME)
                .build();

        UsuarioEntity usuario = Mockito.mock(UsuarioEntity.class);
        PersonaEntity persona = Mockito.mock(PersonaEntity.class);

        given(usuarioJPARepository.findByUsername(USERNAME)).willReturn(Optional.of(usuario));
        given(usuario.getPersona()).willReturn(persona);
        given(persona.getEmail()).willReturn("pepe@gmail.com");

        loginService.recuperarPasswdUsuario(recuperarPass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRecuperarPassWrongUsername() throws MessagingException {
        RecuperarPass recuperarPass = RecuperarPass.builder()
                .withEmail(EMAIL)
                .withUsername(USERNAME)
                .build();

        UsuarioEntity usuario = Mockito.mock(UsuarioEntity.class);
        PersonaEntity persona = Mockito.mock(PersonaEntity.class);

        given(usuarioJPARepository.findByUsername(USERNAME)).willReturn(Optional.of(usuario));
        given(usuario.getPersona()).willReturn(persona);
        given(usuario.getUsername()).willReturn("pepe");
        given(persona.getEmail()).willReturn(EMAIL);

        loginService.recuperarPasswdUsuario(recuperarPass);
    }

    @Test(expected = NoSuchElementException.class)
    public void testRecuperarPassUserNotFound() throws MessagingException {
        RecuperarPass recuperarPass = RecuperarPass.builder()
                .withEmail(EMAIL)
                .withUsername(USERNAME)
                .build();

        given(usuarioJPARepository.findByUsername(USERNAME)).willReturn(Optional.empty());

        loginService.recuperarPasswdUsuario(recuperarPass);
    }

    @Test(expected = NoSuchElementException.class)
    public void testRecuperarPassPersonNotFound() throws MessagingException {
        RecuperarPass recuperarPass = RecuperarPass.builder()
                .withEmail(EMAIL)
                .withUsername(USERNAME)
                .build();

        UsuarioEntity usuario = Mockito.mock(UsuarioEntity.class);

        given(usuarioJPARepository.findByUsername(USERNAME)).willReturn(Optional.of(usuario));
        given(usuario.getPersona()).willReturn(null);

        loginService.recuperarPasswdUsuario(recuperarPass);
    }

    private static UsuarioEntity buildUsuarioEntity() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(1);
        usuarioEntity.setUsername(USERNAME);
        usuarioEntity.setPassword(PASSWORD);
        usuarioEntity.setBorrado(0);

        return usuarioEntity;
    }

    private static UsuarioEntity buildUsuarioEntityNoActivo() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(1);
        usuarioEntity.setUsername(USERNAME);
        usuarioEntity.setPassword(PASSWORD);
        usuarioEntity.setBorrado(1);

        return usuarioEntity;
    }

    private static String generateContenidoEmail(final String fechaEmail, final String mensajeEmail) {
        return Constantes.TABULACION_FECHA + fechaEmail + Constantes.SALTO_LINEA + Constantes.SALUDO_MAIL
                + Constantes.SALTO_LINEA + mensajeEmail + Constantes.SALTO_LINEA + Constantes.CUERPO_MAIL2
                + Constantes.SALTO_LINEA + Constantes.SALTO_LINEA
                + Constantes.TABULACION_DESPEDIDA + Constantes.DESPEDIDA_MAIL + Constantes.SALTO_LINEA
                + Constantes.TABULACION_FIRMA + Constantes.FIRMA_MAIL;
    }
}