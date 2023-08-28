package com.tfg.gestorcuentas.controller;

import com.tfg.gestorcuentas.data.entity.*;
import com.tfg.gestorcuentas.service.movimiento.IMovimientoService;
import com.tfg.gestorcuentas.service.movimiento.model.Movimiento;
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
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MovimientoControllerTest {

    @Mock
    private IMovimientoService iMovimientoService;
    @InjectMocks
    private MovimientoController movimientoController;

    @Test
    public void testSaveMovimientoOK() {
        MovimientoEntity movimientoEntity = buildMovimientoEntityToSave();
        CuentaBancariaEntity cuentaBancariaEntity = buildCuentaBancariaEntity();
        Movimiento movimiento = new Movimiento(cuentaBancariaEntity, movimientoEntity, Collections.emptyList());

        given(iMovimientoService.saveNordigen(movimiento)).willReturn(Messages.MOVIMIENTOS_GUARDADO_OK.getMessage());

        ResponseEntity<?> response = movimientoController.saveNordigenMovimientos(movimiento);

        verify(iMovimientoService).saveNordigen(movimiento);

        Assert.assertEquals(Messages.MOVIMIENTOS_GUARDADO_OK.getMessage(), response.getBody());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testSaveMovimientoInternalServerError() {
        MovimientoEntity movimientoEntity = buildMovimientoEntityInvalid();
        CuentaBancariaEntity cuentaBancariaEntity = buildCuentaBancariaEntity();
        Movimiento movimiento = new Movimiento(cuentaBancariaEntity, movimientoEntity, Collections.emptyList());

        given(iMovimientoService.saveNordigen(movimiento)).willThrow(IllegalArgumentException.class);

        ResponseEntity<?> response = movimientoController.saveNordigenMovimientos(movimiento);

        verify(iMovimientoService).saveNordigen(movimiento);

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testSaveMovimientoBadRequest() {
        MovimientoEntity movimientoEntity = buildMovimientoEntityToSave();
        CuentaBancariaEntity cuentaBancariaEntity = buildCuentaBancariaEntity();
        Movimiento movimiento = new Movimiento(cuentaBancariaEntity, movimientoEntity, Collections.emptyList());

        given(iMovimientoService.saveNordigen(movimiento)).willReturn(StringUtils.EMPTY);

        ResponseEntity<?> response = movimientoController.saveNordigenMovimientos(movimiento);

        verify(iMovimientoService).saveNordigen(movimiento);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    private MovimientoEntity buildMovimientoEntityToSave() {
        Set<CategoriaEntity> categoriaEntitySet = new HashSet<>();
        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setId(1);
        categoriaEntitySet.add(categoria);

        MovimientoEntity entity = new MovimientoEntity();
        entity.setCuentaBancaria(buildCuentaBancariaEntity());
        entity.setCategoriaEntitySet(categoriaEntitySet);
        entity.setIdTransaccion("transactionId");
        entity.setCantidad("123,00");
        entity.setDivisa("Divisa");
        entity.setDestinatario("ES23456789876545");
        entity.setFecha(new Date());
        entity.setInformacionMovimiento("NBA");
        entity.setBorrado(0);

        return entity;
    }

    private MovimientoEntity buildMovimientoEntityToModify() {
        Set<CategoriaEntity> categoriaEntitySet = new HashSet<>();
        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setId(3);
        categoriaEntitySet.add(categoria);

        MovimientoEntity entity = new MovimientoEntity();
        entity.setId(1);
        entity.setCategoriaEntitySet(categoriaEntitySet);

        return entity;
    }

    private MovimientoEntity buildMovimientoEntityInvalid() {
        Set<CategoriaEntity> categoriaEntitySet = new HashSet<>();
        categoriaEntitySet.add(buildCategoriaEntity());

        MovimientoEntity entity = new MovimientoEntity();
        entity.setCuentaBancaria(buildCuentaBancariaEntity());
        entity.setDivisa("Divisa");
        entity.setDestinatario("ES23456789876545");
        entity.setFecha(new Date());
        entity.setBorrado(0);

        return entity;
    }

    private CuentaBancariaEntity buildCuentaBancariaEntity() {
        CuentaBancariaEntity cuentaBancaria = new CuentaBancariaEntity();
        cuentaBancaria.setId(123);
        cuentaBancaria.setUsuario(buildUsuarioEntity());
        cuentaBancaria.setNombre("Nombre cuenta bancaria");
        cuentaBancaria.setMoneda("EUR");
        cuentaBancaria.setProveedor(buildProveedorEntity());
        cuentaBancaria.setFecha(new Date());
        cuentaBancaria.setIban("ES3454343433443344");
        cuentaBancaria.setCantidad(123.00);
        cuentaBancaria.setAccountID("accountId");
        cuentaBancaria.setBorrado(0);

        return cuentaBancaria;
    }

    private CuentaBancariaEntity buildCuentaBancariaEntityInvalid() {
        CuentaBancariaEntity cuentaBancaria = new CuentaBancariaEntity();
        cuentaBancaria.setId(123);
        cuentaBancaria.setUsuario(buildUsuarioEntity());
        cuentaBancaria.setNombre("Nombre cuenta bancaria");
        cuentaBancaria.setMoneda("EUR");
        cuentaBancaria.setAccountID("accountId");
        cuentaBancaria.setBorrado(0);

        return cuentaBancaria;
    }

    private UsuarioEntity buildUsuarioEntity() {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(1);
        entity.setUsername("javier");
        entity.setPassword("javier");
        entity.setBorrado(0);

        return entity;
    }

    private ProveedorEntity buildProveedorEntity() {
        ProveedorEntity entity = new ProveedorEntity();
        entity.setId(456);
        entity.setName("GoCardless");

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

}
