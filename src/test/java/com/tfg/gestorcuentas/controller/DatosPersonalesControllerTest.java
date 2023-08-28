package com.tfg.gestorcuentas.controller;

import com.tfg.gestorcuentas.data.entity.PersonaEntity;
import com.tfg.gestorcuentas.data.entity.UsuarioEntity;
import com.tfg.gestorcuentas.service.datosPersonales.IDatosPersonalesService;
import com.tfg.gestorcuentas.service.datosPersonales.model.DatosPersonales;
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

import java.util.NoSuchElementException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DatosPersonalesControllerTest {

    private static final String DNI = "444555333R";
    private static final String USERNAME = "javier";

    @Mock
    private IDatosPersonalesService iDatosPersonalesService;

    private DatosPersonalesController datosPersonalesController;

    @Before
    public void setUp() {
        datosPersonalesController = new DatosPersonalesController(iDatosPersonalesService);
    }

    @Test
    public void testUpdateDatosPersonalesOk() {
        UsuarioEntity usuario = buildUsuarioEntity();
        PersonaEntity persona = buildPersonaEntity(usuario);
        String resultado = Messages.PERSONAL_DATA_MODIFIED.getMessage();

        DatosPersonales datosPersonales = new DatosPersonales(persona, usuario);

        given(iDatosPersonalesService.updateDatosPersonales(datosPersonales)).willReturn(resultado);

        ResponseEntity<?> result = datosPersonalesController.updateDatosPersonales(datosPersonales);

        verify(iDatosPersonalesService).updateDatosPersonales(datosPersonales);

        Assert.assertEquals(Messages.PERSONAL_DATA_MODIFIED.getMessage(), resultado);
        Assert.assertEquals(Messages.PERSONAL_DATA_MODIFIED.getMessage(), result.getBody());
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testUpdateDatosPersonalesInternalServerError() {
        UsuarioEntity usuario = buildUsuarioEntity();

        DatosPersonales datosPersonales = new DatosPersonales(null, usuario);

        given(iDatosPersonalesService.updateDatosPersonales(datosPersonales))
                .willThrow(NoSuchElementException.class);

        ResponseEntity<?> result = datosPersonalesController.updateDatosPersonales(datosPersonales);

        verify(iDatosPersonalesService).updateDatosPersonales(datosPersonales);

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    public void testUpdateDatosPersonalesBadRequest() {

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(1);
        usuario.setUsername(null);
        usuario.setPassword("javier");
        usuario.setBorrado(0);

        PersonaEntity persona = buildPersonaEntity(usuario);

        DatosPersonales datosPersonales = new DatosPersonales(persona, usuario);

        given(iDatosPersonalesService.updateDatosPersonales(datosPersonales))
                .willThrow(IllegalArgumentException.class);

        ResponseEntity<?> result = datosPersonalesController.updateDatosPersonales(datosPersonales);

        verify(iDatosPersonalesService).updateDatosPersonales(datosPersonales);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testUpdateDatosPersonaleServiceResultNull() {
        UsuarioEntity usuario = buildUsuarioEntity();
        PersonaEntity persona = buildPersonaEntity(usuario);
        String resultado = StringUtils.EMPTY;

        DatosPersonales datosPersonales = new DatosPersonales(persona, usuario);

        given(iDatosPersonalesService.updateDatosPersonales(datosPersonales)).willReturn(resultado);

        ResponseEntity<?> result = datosPersonalesController.updateDatosPersonales(datosPersonales);

        verify(iDatosPersonalesService).updateDatosPersonales(datosPersonales);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testFindByUsername() {
        UsuarioEntity usuario = buildUsuarioEntity();
        PersonaEntity persona = buildPersonaEntity(usuario);
        DatosPersonales datosPersonales = new DatosPersonales(persona, usuario);

        given(iDatosPersonalesService.findByUsername("javier")).willReturn(datosPersonales);

        ResponseEntity<?> result = datosPersonalesController.findDatosPersonales("javier");

        verify(iDatosPersonalesService).findByUsername("javier");

        Assert.assertEquals(datosPersonales, result.getBody());
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testFindByUsernameNoSuchElement() {
        given(iDatosPersonalesService.findByUsername("javier")).willThrow(NoSuchElementException.class);

        ResponseEntity<?> result = datosPersonalesController.findDatosPersonales("javier");

        verify(iDatosPersonalesService).findByUsername("javier");

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    public void testFindByUsernameIllegalArgument() {
        given(iDatosPersonalesService.findByUsername("javier")).willThrow(IllegalArgumentException.class);

        ResponseEntity<?> result = datosPersonalesController.findDatosPersonales("javier");

        verify(iDatosPersonalesService).findByUsername("javier");

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    public void testFindByUsernameNull() {
        given(iDatosPersonalesService.findByUsername("javier")).willReturn(null);

        ResponseEntity<?> result = datosPersonalesController.findDatosPersonales("javier");

        verify(iDatosPersonalesService).findByUsername("javier");

        Assert.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
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

    private static UsuarioEntity buildUsuarioEntity() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(1);
        usuario.setUsername(USERNAME);
        usuario.setPassword("javier");
        usuario.setBorrado(0);
        return usuario;
    }}