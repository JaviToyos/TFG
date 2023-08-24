package com.tfg.gestorcuentas.controller;

import com.tfg.gestorcuentas.data.entity.PersonaEntity;
import com.tfg.gestorcuentas.data.entity.UsuarioEntity;
import com.tfg.gestorcuentas.service.registro.IRegistroService;
import com.tfg.gestorcuentas.service.registro.model.Registro;
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
public class RegistroControllerTest {

    @Mock
    private IRegistroService registroService;
    private RegistroController registroController;

    @Before
    public void setUp() {
        registroController = new RegistroController(registroService);
    }

    @Test
    public void testRegister() {
        Registro registro = new Registro(providePersonaEntity(), provideUsuarioEntity());

        given(registroService.registro(registro)).willReturn(Messages.GUARDADO_OK.getMessage());

        ResponseEntity<String> response = registroController.registro(registro);

        verify(registroService).registro(registro);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), Messages.GUARDADO_OK.getMessage());
    }

    @Test
    public void testRegisterIllegalArgument() {
        Registro registro = new Registro(null, provideUsuarioEntity());

        given(registroService.registro(registro)).willThrow(IllegalArgumentException.class);

        ResponseEntity<String> response = registroController.registro(registro);

        verify(registroService).registro(registro);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void testRegisterBadRequest() {
        given(registroService.registro(null)).willReturn(StringUtils.EMPTY);

        ResponseEntity<String> response = registroController.registro(null);

        verify(registroService).registro(null);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    private PersonaEntity providePersonaEntity() {
        PersonaEntity entity = new PersonaEntity();
        entity.setId(1);
        entity.setNombre("Maria");
        entity.setApellidos("Borras");
        entity.setDni("55578765E");
        entity.setEmail("mborras@gmail.com");
        entity.setBorrado(0);

        return entity;
    }

    private UsuarioEntity provideUsuarioEntity() {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(2);
        entity.setUsername("mborras");
        entity.setPassword("mborras");
        entity.setBorrado(0);

        return entity;
    }
}
