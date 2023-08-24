package com.tfg.gestorcuentas.controller;

import com.tfg.gestorcuentas.data.entity.CategoriaEntity;
import com.tfg.gestorcuentas.data.entity.CriterioEntity;
import com.tfg.gestorcuentas.data.entity.UsuarioEntity;
import com.tfg.gestorcuentas.service.criterio.ICriterioService;
import com.tfg.gestorcuentas.service.criterio.model.Criterio;
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
public class CriterioControllerTest {

    @Mock
    private ICriterioService iCriterioService;
    @InjectMocks
    private CriterioController criterioController;

    @Test
    public void testSaveCriterio() {
        Criterio criterio = new Criterio(buildCategoriaEntity(), "NBA");

        given(iCriterioService.save(criterio)).willReturn(Messages.CRITERIO_SAVED.getMessage());

        ResponseEntity<?> response = criterioController.addCriterio(criterio);

        verify(iCriterioService).save(criterio);

        Assert.assertEquals(Messages.CRITERIO_SAVED.getMessage(), response.getBody());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testSaveCriterioInternalServerError() {
        Criterio criterio = new Criterio(null, "NBA");

        given(iCriterioService.save(criterio)).willThrow(NoSuchElementException.class);

        ResponseEntity<?> response = criterioController.addCriterio(criterio);

        verify(iCriterioService).save(criterio);

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testSaveCriterioBadRequest() {
        Criterio criterio = new Criterio(buildCategoriaEntity(), "NBA");

        given(iCriterioService.save(criterio)).willReturn(StringUtils.EMPTY);

        ResponseEntity<?> response = criterioController.addCriterio(criterio);

        verify(iCriterioService).save(criterio);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testFindCriterioByCategoria() {
        CriterioEntity criterio = new CriterioEntity();
        criterio.setId(1);
        criterio.setNombre("NBA");
        criterio.setBorrado(0);
        criterio.setCategoria(buildCategoriaEntity());

        given(iCriterioService.findByCategoria(1)).willReturn(Collections.singletonList(criterio));

        ResponseEntity<?> response = criterioController.findCriterios(1);

        verify(iCriterioService).findByCategoria(1);

        Assert.assertEquals(Collections.singletonList(criterio), response.getBody());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testFindCriterioByCategoriaNoSuchElement() {
        given(iCriterioService.findByCategoria(1)).willThrow(NoSuchElementException.class);

        ResponseEntity<?> response = criterioController.findCriterios(1);

        verify(iCriterioService).findByCategoria(1);

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testFindCriterioByCategoriaIllegalArgument() {
        given(iCriterioService.findByCategoria(1)).willThrow(IllegalArgumentException.class);

        ResponseEntity<?> response = criterioController.findCriterios(1);

        verify(iCriterioService).findByCategoria(1);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    public void testFindCriterioByCategoriaBadRequest() {
        given(iCriterioService.findByCategoria(1)).willReturn(null);

        ResponseEntity<?> response = criterioController.findCriterios(1);

        verify(iCriterioService).findByCategoria(1);

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


    private CategoriaEntity buildCategoriaEntity() {
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setId(1);
        categoriaEntity.setNombre("Deporte");
        categoriaEntity.setBorrado(0);
        categoriaEntity.setUsuario(buildUsuarioEntity());

        return categoriaEntity;
    }

    private CategoriaEntity buildCategoriaEntityInvalid() {
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setNombre("Deporte");
        categoriaEntity.setBorrado(0);
        categoriaEntity.setUsuario(buildUsuarioEntity());

        return categoriaEntity;
    }

    private CriterioEntity buildCriterioEntity() {
        CriterioEntity entity = new CriterioEntity();
        entity.setNombre("NBA");
        entity.setBorrado(0);
        entity.setCategoria(buildCategoriaEntity());

        return entity;
    }

    private CriterioEntity buildCriterioEntityCategoriaInvalid() {
        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setNombre("Nombre");
        categoria.setBorrado(0);

        CriterioEntity entity = new CriterioEntity();
        entity.setNombre("NBA");
        entity.setBorrado(0);
        entity.setCategoria(categoria);

        return entity;
    }
}
