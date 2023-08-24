package com.tfg.gestorcuentas.service.registro;

import com.tfg.gestorcuentas.data.entity.PersonaEntity;
import com.tfg.gestorcuentas.data.entity.UsuarioEntity;
import com.tfg.gestorcuentas.data.repository.IPersonaJPARepository;
import com.tfg.gestorcuentas.data.repository.IUsuarioJPARepository;
import com.tfg.gestorcuentas.service.registro.model.Registro;
import com.tfg.gestorcuentas.utils.Messages;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RegistroServiceTest {

    private static final String DNI = "45321223R";
    private static final String USERNAME = "javi";
    public static final String NOMBRE_PERSONA = "Javier";
    public static final String APELLIDOS_PERSONA = "López López";
    public static final String EMAIL_PERSONA = "javier@gmail.com";
    public static final String PASSWD = "javi";

    @Mock
    private IPersonaJPARepository iPersonaJPARepository;
    @Mock
    private IUsuarioJPARepository iUsuarioJPARepository;

    private RegistroService registroService;


    @Before
    public void setUp() {
        registroService = new RegistroService(iPersonaJPARepository, iUsuarioJPARepository);
    }

    @Test
    public void shouldRegistrar() {
        PersonaEntity persona = buildPersonaEntity();
        UsuarioEntity usuario = buildUsuarioEntity();

        Registro registro = Registro.builder()
                .withPersona(persona)
                .withUsuario(usuario)
                .build();

        given(iPersonaJPARepository.findByDni(DNI)).willReturn(Optional.empty());
        given(iUsuarioJPARepository.findByUsername(USERNAME)).willReturn(Optional.empty());

        usuario.setId(1);
        given(iUsuarioJPARepository.save(usuario)).willReturn(usuario);
        persona.setUsuario(usuario);
        given(iPersonaJPARepository.save(persona)).willReturn(persona);

        String mensajeRegistroOk = registroService.registro(registro);

        verify(iPersonaJPARepository).findByDni(DNI);
        verify(iUsuarioJPARepository).findByUsername(USERNAME);
        verify(iUsuarioJPARepository).save(usuario);
        verify(iPersonaJPARepository).save(persona);

        Assert.assertEquals(Messages.GUARDADO_OK.getMessage(), mensajeRegistroOk);
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenPersonaExists() {
        PersonaEntity persona = buildPersonaEntity();
        UsuarioEntity usuario = buildUsuarioEntity();

        Registro registro = Registro.builder()
                .withPersona(persona)
                .withUsuario(usuario)
                .build();

        given(iPersonaJPARepository.findByDni(DNI)).willReturn(Optional.of(persona));

        registroService.registro(registro);

        verify(iPersonaJPARepository).findByDni(DNI);
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenUsuarioExists() {
        PersonaEntity persona = buildPersonaEntity();
        UsuarioEntity usuario = buildUsuarioEntity();

        Registro registro = Registro.builder()
                .withPersona(persona)
                .withUsuario(usuario)
                .build();

        given(iPersonaJPARepository.findByDni(DNI)).willReturn(Optional.empty());
        given(iUsuarioJPARepository.findByUsername(USERNAME)).willReturn(Optional.of(usuario));

        registroService.registro(registro);

        verify(iPersonaJPARepository).findByDni(DNI);
        verify(iUsuarioJPARepository).findByUsername(USERNAME);
    }

    private static PersonaEntity buildPersonaEntity() {
        PersonaEntity persona = new PersonaEntity();
        persona.setDni(DNI);
        persona.setNombre(NOMBRE_PERSONA);
        persona.setApellidos(APELLIDOS_PERSONA);
        persona.setEmail(EMAIL_PERSONA);
        persona.setBorrado(0);

        return persona;
    }

    private static UsuarioEntity buildUsuarioEntity() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUsername(USERNAME);
        usuario.setPassword(PASSWD);
        usuario.setBorrado(0);

        return usuario;
    }
}