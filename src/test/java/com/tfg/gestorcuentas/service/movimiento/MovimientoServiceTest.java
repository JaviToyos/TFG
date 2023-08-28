package com.tfg.gestorcuentas.service.movimiento;

import com.tfg.gestorcuentas.data.entity.*;
import com.tfg.gestorcuentas.data.repository.ICategoriaJPARepository;
import com.tfg.gestorcuentas.data.repository.ICriterioJPARepository;
import com.tfg.gestorcuentas.data.repository.IMovimientoJPARepository;
import com.tfg.gestorcuentas.service.categoria.model.CategoriaDTO;
import com.tfg.gestorcuentas.service.movimiento.model.Movimiento;
import com.tfg.gestorcuentas.service.movimiento.model.MovimientoDTO;
import com.tfg.gestorcuentas.utils.Messages;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MovimientoServiceTest {
    @Mock
    private IMovimientoJPARepository iMovimientoJPARepository;
    @Mock
    private ICategoriaJPARepository iCategoriaJPARepository;
    @Mock
    private ICriterioJPARepository iCriterioJPARepository;
    @InjectMocks
    private MovimientoService movimientoService;

    @Test
    public void testSaveMovimiento() {
        MovimientoEntity movimientoEntity = buildMovimientoEntity();
        CuentaBancariaEntity cuentaBancariaEntity = buildCuentaBancariaEntity();
        Movimiento movimiento = new Movimiento(cuentaBancariaEntity, movimientoEntity, Collections.emptyList());
        CategoriaEntity categoria = buildCategoriaEntity();
        CriterioEntity criterio = buildCriterioEntity();

        given(iCategoriaJPARepository.findByUsuario_Id(1)).willReturn(Collections.singletonList(categoria));
        given(iCriterioJPARepository.findByCategoria_IdAndBorrado(categoria.getId(), 0)).willReturn(Collections.singletonList(criterio));
        ArgumentCaptor<MovimientoEntity> argument = ArgumentCaptor.forClass(MovimientoEntity.class);

        String msg = movimientoService.saveNordigen(movimiento);

        verify(iCategoriaJPARepository).findByUsuario_Id(1);
        verify(iCriterioJPARepository).findByCategoria_IdAndBorrado(categoria.getId(), 0);
        verify(iMovimientoJPARepository).save(argument.capture());

        Assert.assertEquals(Messages.MOVIMIENTOS_GUARDADO_OK.getMessage(), msg);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveMovimientoMovimientoNotValid() {
        MovimientoEntity movimientoEntity = buildMovimientoEntityInvalid();
        CuentaBancariaEntity cuentaBancariaEntity = buildCuentaBancariaEntity();
        Movimiento movimiento = new Movimiento(cuentaBancariaEntity, movimientoEntity, Collections.emptyList());

        movimientoService.saveNordigen(movimiento);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveMovimientoCuentaBancariaNotValid() {
        MovimientoEntity movimientoEntity = buildMovimientoEntity();
        CuentaBancariaEntity cuentaBancariaEntity = buildCuentaBancariaEntityInvalid();
        Movimiento movimiento = new Movimiento(cuentaBancariaEntity, movimientoEntity, Collections.emptyList());

        movimientoService.saveNordigen(movimiento);
    }

    @Test
    public void testFindByIdCuenta() {
        MovimientoEntity mov = buildMovimientoEntity();
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setId(1);
        categoriaDTO.setNombre("Deporte");

        MovimientoDTO movimientoDTO = new MovimientoDTO();
        movimientoDTO.setId(mov.getId());
        movimientoDTO.setCantidad(mov.getCantidad());
        movimientoDTO.setDivisa(mov.getDivisa());
        movimientoDTO.setFecha(mov.getFecha());
        movimientoDTO.setInformacionMovimiento(mov.getInformacionMovimiento());
        movimientoDTO.setDestinatario(mov.getDestinatario());
        movimientoDTO.setIdTransaccion(mov.getIdTransaccion());
        movimientoDTO.setCategorias(Collections.singletonList(categoriaDTO));

        given(iMovimientoJPARepository.findByCuentaBancaria_Id(1)).willReturn(Collections.singletonList(mov));

        List<MovimientoDTO> movimientoEntityList = movimientoService.findByIdCuenta(1);

        verify(iMovimientoJPARepository).findByCuentaBancaria_Id(1);

        Assert.assertEquals(Collections.singletonList(movimientoDTO), movimientoEntityList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindByIdCuentaIdNull() {
        movimientoService.findByIdCuenta(null);
    }

    private MovimientoEntity buildMovimientoEntity() {
        Set<CategoriaEntity> categoriaEntitySet = new HashSet<>();
        categoriaEntitySet.add(buildCategoriaEntity());

        MovimientoEntity entity = new MovimientoEntity();
        entity.setId(1);
        entity.setCuentaBancaria(buildCuentaBancariaEntity());
        entity.setCategoriaEntitySet(categoriaEntitySet);
        entity.setIdTransaccion("transactionId");
        entity.setCantidad("123.00");
        entity.setDivisa("Divisa");
        entity.setDestinatario("ES23456789876545");
        entity.setFecha(new Date());
        entity.setInformacionMovimiento("NBA");
        entity.setBorrado(0);

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

    private CriterioEntity buildCriterioEntity() {
        CriterioEntity entity = new CriterioEntity();
        entity.setId(1);
        entity.setNombre("NBA");
        entity.setBorrado(0);
        entity.setCategoria(buildCategoriaEntity());

        return entity;
    }
}
