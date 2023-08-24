package com.tfg.gestorcuentas.service.datosPersonales;

import com.tfg.gestorcuentas.data.entity.PersonaEntity;
import com.tfg.gestorcuentas.data.entity.UsuarioEntity;
import com.tfg.gestorcuentas.data.repository.IPersonaJPARepository;
import com.tfg.gestorcuentas.data.repository.IUsuarioJPARepository;
import com.tfg.gestorcuentas.service.datosPersonales.model.DatosPersonales;
import com.tfg.gestorcuentas.utils.Messages;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DatosPersonalesServiceTest {

    public static final String DNI = "444333555T";
    public static final String USERNAME = "javier";
    @Mock
    private IUsuarioJPARepository iUsuarioJPARepository;
    @Mock
    private IPersonaJPARepository iPersonaJPARepository;

    private DatosPersonalesService datosPersonalesService;

    @Before
    public void setUp() throws Exception {
        datosPersonalesService = new DatosPersonalesService(iPersonaJPARepository, iUsuarioJPARepository);
    }

    @Test
    public void updateDatosPersonales() {
        UsuarioEntity usuario = builUsuarioEntity();
        PersonaEntity persona = buildPersonaEntity(usuario);
        usuario.setPersona(persona);

        UsuarioEntity usuarioExpected = new UsuarioEntity();
        usuarioExpected.setId(1);
        usuarioExpected.setUsername(USERNAME);
        usuarioExpected.setPassword("javierperez");
        usuarioExpected.setBorrado(0);

        PersonaEntity personaExpected = new PersonaEntity();
        personaExpected.setId(1);
        personaExpected.setDni(DNI);
        personaExpected.setNombre("Javier");
        personaExpected.setApellidos("Pérez López");
        personaExpected.setEmail("javier1@gmail.com");
        personaExpected.setBorrado(0);
        usuarioExpected.setPersona(personaExpected);

        DatosPersonales datosPersonales = new DatosPersonales(personaExpected, usuarioExpected);

        given(iUsuarioJPARepository.findByUsername(USERNAME)).willReturn(Optional.of(usuario));
        given(iUsuarioJPARepository.save(usuarioExpected)).willReturn(usuarioExpected);

        String resultado = datosPersonalesService.updateDatosPersonales(datosPersonales);

        verify(iUsuarioJPARepository).findByUsername(USERNAME);
        verify(iUsuarioJPARepository).save(usuarioExpected);
        verify(iPersonaJPARepository).save(personaExpected);

        Assert.assertEquals(resultado, Messages.PERSONAL_DATA_MODIFIED.getMessage());
    }

    @Test(expected = NoSuchElementException.class)
    public void updateDatosPersonalesUsuarioNotExists() {
        PersonaEntity persona = buildPersonaEntity(null);

        UsuarioEntity usuarioExpected = new UsuarioEntity();
        usuarioExpected.setId(1);
        usuarioExpected.setUsername(USERNAME);
        usuarioExpected.setPassword("javierperez");
        usuarioExpected.setBorrado(0);

        PersonaEntity personaExpected = new PersonaEntity();
        personaExpected.setId(1);
        personaExpected.setDni(DNI);
        personaExpected.setNombre("Javier");
        personaExpected.setApellidos("Pérez López");
        personaExpected.setEmail("javier1@gmail.com");
        personaExpected.setBorrado(0);
        personaExpected.setUsuario(usuarioExpected);

        DatosPersonales datosPersonales = new DatosPersonales(personaExpected, usuarioExpected);

        given(iUsuarioJPARepository.findByUsername(USERNAME)).willReturn(Optional.empty());

        datosPersonalesService.updateDatosPersonales(datosPersonales);

        verify(iUsuarioJPARepository).findByUsername(USERNAME);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateDatosPersonalesPersonaNull() {
        UsuarioEntity usuarioExpected = new UsuarioEntity();
        usuarioExpected.setId(1);
        usuarioExpected.setUsername(USERNAME);
        usuarioExpected.setPassword("javierperez");
        usuarioExpected.setBorrado(0);

        PersonaEntity personaExpected = new PersonaEntity();
        personaExpected.setId(1);
        personaExpected.setDni(DNI);
        personaExpected.setNombre(null);
        personaExpected.setApellidos("Pérez López");
        personaExpected.setEmail("javier1@gmail.com");
        personaExpected.setBorrado(0);
        personaExpected.setUsuario(usuarioExpected);

        DatosPersonales datosPersonales = new DatosPersonales(personaExpected, usuarioExpected);

        datosPersonalesService.updateDatosPersonales(datosPersonales);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateDatosPersonalesUsuarioNull() {
        UsuarioEntity usuarioExpected = new UsuarioEntity();
        usuarioExpected.setId(1);
        usuarioExpected.setUsername(null);
        usuarioExpected.setPassword("javierperez");
        usuarioExpected.setBorrado(0);

        PersonaEntity personaExpected = new PersonaEntity();
        personaExpected.setId(1);
        personaExpected.setDni(DNI);
        personaExpected.setNombre("Javier");
        personaExpected.setApellidos("Pérez López");
        personaExpected.setEmail("javier1@gmail.com");
        personaExpected.setBorrado(0);
        personaExpected.setUsuario(usuarioExpected);

        DatosPersonales datosPersonales = new DatosPersonales(personaExpected, usuarioExpected);

        datosPersonalesService.updateDatosPersonales(datosPersonales);
    }

    private static PersonaEntity buildPersonaEntity(UsuarioEntity user) {
        PersonaEntity persona = new PersonaEntity();
        persona.setId(1);
        persona.setDni(DNI);
        persona.setNombre("Javier");
        persona.setApellidos("Toyos Caamano");
        persona.setEmail("javier@gmail.com");
        persona.setBorrado(0);
        persona.setUsuario(user);

        return persona;
    }

    private static UsuarioEntity builUsuarioEntity() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(1);
        usuario.setUsername(USERNAME);
        usuario.setPassword("javier");
        usuario.setBorrado(0);
        return usuario;
    }
}