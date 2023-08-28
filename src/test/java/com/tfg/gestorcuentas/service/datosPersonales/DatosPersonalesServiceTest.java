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

        UsuarioEntity usuarioExpected = new UsuarioEntity();
        usuarioExpected.setId(1);
        usuarioExpected.setUsername(USERNAME);
        usuarioExpected.setPassword("javierperez");
        usuarioExpected.setBorrado(0);

        PersonaEntity personaBD = new PersonaEntity();
        personaBD.setId(1);
        personaBD.setDni(DNI);
        personaBD.setNombre("Javier");
        personaBD.setApellidos("Pérez");
        personaBD.setEmail("javier@gmail.com");
        personaBD.setBorrado(0);

        PersonaEntity personaExpected = new PersonaEntity();
        personaExpected.setId(1);
        personaExpected.setDni(DNI);
        personaExpected.setNombre("Javier");
        personaExpected.setApellidos("Pérez López");
        personaExpected.setEmail("javier1@gmail.com");
        personaExpected.setBorrado(0);

        DatosPersonales datosPersonales = new DatosPersonales(personaExpected, usuarioExpected);

        given(iUsuarioJPARepository.findByUsername(USERNAME)).willReturn(Optional.of(usuario));
        given(iUsuarioJPARepository.save(usuarioExpected)).willReturn(usuarioExpected);
        given(iPersonaJPARepository.findByUsuario(usuario)).willReturn(Optional.of(personaBD));

        String resultado = datosPersonalesService.updateDatosPersonales(datosPersonales);

        verify(iUsuarioJPARepository).findByUsername(USERNAME);
        verify(iPersonaJPARepository).findByUsuario(usuario);
        verify(iUsuarioJPARepository).save(usuarioExpected);
        verify(iPersonaJPARepository).save(personaExpected);

        Assert.assertEquals(resultado, Messages.PERSONAL_DATA_MODIFIED.getMessage());
    }

    @Test(expected = NoSuchElementException.class)
    public void updateDatosPersonalesUsuarioNotExists() {
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

    @Test(expected = NoSuchElementException.class)
    public void updateDatosPersonalesPersonaNotExists() {
        UsuarioEntity usuario = builUsuarioEntity();

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

        given(iUsuarioJPARepository.findByUsername(USERNAME)).willReturn(Optional.of(usuario));
        given(iPersonaJPARepository.findByUsuario(usuario)).willReturn(Optional.empty());

        datosPersonalesService.updateDatosPersonales(datosPersonales);

        verify(iUsuarioJPARepository).findByUsername(USERNAME);
        verify(iPersonaJPARepository).findByUsuario(usuario);
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

    @Test
    public void testFindByUsername() {
        UsuarioEntity usuario = builUsuarioEntity();
        PersonaEntity personaExpected = new PersonaEntity();
        personaExpected.setId(1);
        personaExpected.setDni(DNI);
        personaExpected.setNombre("Javier");
        personaExpected.setApellidos("Pérez López");
        personaExpected.setEmail("javier1@gmail.com");
        personaExpected.setBorrado(0);
        usuario.setPersona(personaExpected);

        given(iUsuarioJPARepository.findByUsername("javier")).willReturn(Optional.of(usuario));

        DatosPersonales dataExpected = datosPersonalesService.findByUsername("javier");

        verify(iUsuarioJPARepository).findByUsername("javier");

        Assert.assertEquals(DatosPersonales.builder().withUsuario(usuario).withPersona(personaExpected).build(), dataExpected);
    }

    @Test
    public void testFindByUsernameDataInvalid() {
        UsuarioEntity usuario = builUsuarioEntityInvalid();
        PersonaEntity personaExpected = new PersonaEntity();
        personaExpected.setId(1);
        personaExpected.setDni(DNI);
        personaExpected.setNombre("Javier");
        personaExpected.setApellidos("Pérez López");
        personaExpected.setEmail("javier1@gmail.com");
        personaExpected.setBorrado(0);
        usuario.setPersona(personaExpected);

        given(iUsuarioJPARepository.findByUsername("javier")).willReturn(Optional.of(usuario));

        DatosPersonales dataExpected = datosPersonalesService.findByUsername("javier");

        verify(iUsuarioJPARepository).findByUsername("javier");

        Assert.assertNull(dataExpected);
    }

    @Test(expected = NoSuchElementException.class)
    public void testFindByUsernameNoSuchUser() {
        given(iUsuarioJPARepository.findByUsername("javier")).willReturn(Optional.empty());

        datosPersonalesService.findByUsername("javier");

        verify(iUsuarioJPARepository).findByUsername("javier");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindByUsernameEmpty() {
        datosPersonalesService.findByUsername("");
    }

    private static UsuarioEntity builUsuarioEntity() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(1);
        usuario.setUsername(USERNAME);
        usuario.setPassword("javier");
        usuario.setBorrado(0);
        return usuario;
    }

    private static UsuarioEntity builUsuarioEntityInvalid() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(1);
        usuario.setUsername(null);
        usuario.setPassword("javier");
        usuario.setBorrado(0);
        return usuario;
    }
}