package com.tfg.gestorcuentas.controller;

import com.tfg.gestorcuentas.data.entity.CategoriaEntity;
import com.tfg.gestorcuentas.data.entity.UsuarioEntity;
import com.tfg.gestorcuentas.service.categoria.ICategoriaService;
import com.tfg.gestorcuentas.service.categoria.model.Categoria;
import com.tfg.gestorcuentas.utils.Messages;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.NoSuchElementException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CategoriaControllerTest {

    @Mock
    private ICategoriaService service;
    @InjectMocks
    private CategoriaController categoriaController;

    @Test
    public void testSave() {
        Categoria categoria = new Categoria(buildUsuarioEntity(), "Nueva categoría");

        given(service.save(categoria)).willReturn(Messages.CATEGORIA_GUARDADA.getMessage());

        ResponseEntity<?> response = categoriaController.save(categoria);

        verify(service).save(categoria);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), Messages.CATEGORIA_GUARDADA.getMessage());
    }

    @Test
    public void testSaveInternalServerError() {
        Categoria categoria = new Categoria(buildUsuarioEntity(), "");

        given(service.save(categoria)).willThrow(IllegalArgumentException.class);

        ResponseEntity<?> response = categoriaController.save(categoria);

        verify(service).save(categoria);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void testSaveBadRequest() {
        Categoria categoria = new Categoria(buildUsuarioEntity(), "");

        given(service.save(categoria)).willReturn(StringUtils.EMPTY);

        ResponseEntity<?> response = categoriaController.save(categoria);

        verify(service).save(categoria);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testModified() {
        Categoria categoria = new Categoria(buildUsuarioEntity(), "Nueva categoría");

        given(service.modify(categoria)).willReturn(Messages.CATEGORIA_MODIFIED.getMessage());

        ResponseEntity<?> response = categoriaController.modify(categoria);

        verify(service).modify(categoria);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), Messages.CATEGORIA_MODIFIED.getMessage());
    }

    @Test
    public void testModifyInternalServerError() {
        Categoria categoria = new Categoria(buildUsuarioEntity(), "");

        given(service.modify(categoria)).willThrow(IllegalArgumentException.class);

        ResponseEntity<?> response = categoriaController.modify(categoria);

        verify(service).modify(categoria);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void testModifyBadRequest() {
        Categoria categoria = new Categoria(buildUsuarioEntity(), "");

        given(service.modify(categoria)).willReturn(StringUtils.EMPTY);

        ResponseEntity<?> response = categoriaController.modify(categoria);

        verify(service).modify(categoria);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testFindByUsername() {
        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setId(1);
        categoria.setNombre("Categoria");
        categoria.setBorrado(0);
        categoria.setUsuario(buildUsuarioEntity());

        given(service.findByUsername("javier")).willReturn(Collections.singletonList(categoria));

        ResponseEntity<?> response = categoriaController.findCategoriaByUsername("javier");

        verify(service).findByUsername("javier");

        Assert.assertEquals(Collections.singletonList(categoria), response.getBody());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testFindByUsernameThrowNoSuchElementException() {
        given(service.findByUsername("javier")).willThrow(NoSuchElementException.class);

        ResponseEntity<?> response = categoriaController.findCategoriaByUsername("javier");

        verify(service).findByUsername("javier");

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testFindByUsernameThrowIllegalArgumentException() {
        given(service.findByUsername("javier")).willThrow(IllegalArgumentException.class);

        ResponseEntity<?> response = categoriaController.findCategoriaByUsername("javier");

        verify(service).findByUsername("javier");

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testFindByUsernameBadRequest() {
        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setId(1);
        categoria.setNombre("Categoria");
        categoria.setBorrado(0);
        categoria.setUsuario(buildUsuarioEntity());

        given(service.findByUsername("javier")).willReturn(null);

        ResponseEntity<?> response = categoriaController.findCategoriaByUsername("javier");

        verify(service).findByUsername("javier");

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    private UsuarioEntity buildUsuarioEntity() {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(1);
        entity.setUsername("javier");
        entity.setPassword("javier");
        entity.setBorrado(0);

        return entity;
    }
}
