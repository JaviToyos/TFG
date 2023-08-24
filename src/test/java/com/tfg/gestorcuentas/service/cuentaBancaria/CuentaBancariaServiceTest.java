package com.tfg.gestorcuentas.service.cuentaBancaria;

import com.tfg.gestorcuentas.data.entity.CuentaBancariaEntity;
import com.tfg.gestorcuentas.data.entity.ProveedorEntity;
import com.tfg.gestorcuentas.data.entity.UsuarioEntity;
import com.tfg.gestorcuentas.data.repository.ICuentaBancariaJPARepository;
import com.tfg.gestorcuentas.data.repository.IProveedorJPARepository;
import com.tfg.gestorcuentas.data.repository.IUsuarioJPARepository;
import com.tfg.gestorcuentas.service.cuentaBancaria.model.CuentaBancaria;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.balance.AccountBalance;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.balance.BalanceAmount;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.details.AccountDetails;
import com.tfg.gestorcuentas.utils.Messages;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CuentaBancariaServiceTest {

    @Mock
    private ICuentaBancariaJPARepository cuentaBancariaJPARepository;
    @Mock
    private IUsuarioJPARepository iUsuarioJPARepository;
    @Mock
    private IProveedorJPARepository iProveedorJPARepository;

    private ICuentaBancariaService cuentaBancariaService;

    @Before
    public void setUp() {
        cuentaBancariaService = new CuentaBancariaService(cuentaBancariaJPARepository, iUsuarioJPARepository, iProveedorJPARepository);
    }

    @Test
    public void saveCuentaBancaria() {
        CuentaBancaria cuentaBancaria = CuentaBancaria.builder()
                .withAccountBalance(provideAccountBalance())
                .withAccountDetails(provideAccountDetails())
                .withAccountID("accountId")
                .withNombre("My account")
                .withProveedor(provideProovedor())
                .withUsuario(provideUsuario())
                .build();

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(1);
        usuarioEntity.setUsername("javier");
        usuarioEntity.setPassword("javier");
        usuarioEntity.setBorrado(0);

        ProveedorEntity proveedorEntity = new ProveedorEntity();
        proveedorEntity.setId(1);
        proveedorEntity.setName("GoCardless");

        CuentaBancariaEntity entity = new CuentaBancariaEntity();
        entity.setAccountID("accountId");
        entity.setCantidad(123456.00);
        entity.setBorrado(0);
        entity.setIban("ES208054346756565756");
        entity.setFecha(new Date());
        entity.setMoneda("Euro");
        entity.setProveedor(proveedorEntity);
        entity.setUsuario(usuarioEntity);
        entity.setNombre("My account");

        ArgumentCaptor<CuentaBancariaEntity> argument = ArgumentCaptor.forClass(CuentaBancariaEntity.class);

        given(iUsuarioJPARepository.findByUsername("javier")).willReturn(Optional.of(usuarioEntity));
        given(iProveedorJPARepository.findByName("GoCardless")).willReturn(Optional.of(proveedorEntity));
        given(cuentaBancariaJPARepository.findByAccountID("accountId")).willReturn(Optional.empty());

        String msg = cuentaBancariaService.saveNordigen(cuentaBancaria);

        verify(iUsuarioJPARepository).findByUsername("javier");
        verify(iProveedorJPARepository).findByName("GoCardless");
        verify(cuentaBancariaJPARepository).findByAccountID("accountId");
        verify(cuentaBancariaJPARepository).save(argument.capture());

        Assert.assertEquals(Messages.CUENTA_BANCARIA_GUARDADA.getMessage(), msg);
    }

    @Test
    public void saveCuentaBancariaThatIPreviouslyDeleted() {
        CuentaBancaria cuentaBancaria = CuentaBancaria.builder()
                .withAccountBalance(provideAccountBalance())
                .withAccountDetails(provideAccountDetails())
                .withAccountID("accountId")
                .withNombre("My account")
                .withProveedor(provideProovedor())
                .withUsuario(provideUsuario())
                .build();

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(1);
        usuarioEntity.setUsername("javier");
        usuarioEntity.setPassword("javier");
        usuarioEntity.setBorrado(0);

        ProveedorEntity proveedorEntity = new ProveedorEntity();
        proveedorEntity.setId(1);
        proveedorEntity.setName("GoCardless");


        CuentaBancariaEntity entityDeleted = new CuentaBancariaEntity();
        entityDeleted.setAccountID("accountId");
        entityDeleted.setCantidad(123456.00);
        entityDeleted.setBorrado(1);
        entityDeleted.setIban("ES208054346756565756");
        entityDeleted.setFecha(new Date());
        entityDeleted.setMoneda("Euro");
        entityDeleted.setProveedor(proveedorEntity);
        entityDeleted.setUsuario(usuarioEntity);
        entityDeleted.setNombre("My account");

        ArgumentCaptor<CuentaBancariaEntity> argument = ArgumentCaptor.forClass(CuentaBancariaEntity.class);

        given(iUsuarioJPARepository.findByUsername("javier")).willReturn(Optional.of(usuarioEntity));
        given(iProveedorJPARepository.findByName("GoCardless")).willReturn(Optional.of(proveedorEntity));
        given(cuentaBancariaJPARepository.findByAccountID("accountId")).willReturn(Optional.of(entityDeleted));

        String msg = cuentaBancariaService.saveNordigen(cuentaBancaria);

        verify(iUsuarioJPARepository).findByUsername("javier");
        verify(iProveedorJPARepository).findByName("GoCardless");
        verify(cuentaBancariaJPARepository).findByAccountID("accountId");
        verify(cuentaBancariaJPARepository).save(argument.capture());

        Assert.assertEquals(Messages.CUENTA_BANCARIA_GUARDADA.getMessage(), msg);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveCuentaBancariaAlreadyExists() {
        CuentaBancaria cuentaBancaria = CuentaBancaria.builder()
                .withAccountBalance(provideAccountBalance())
                .withAccountDetails(provideAccountDetails())
                .withAccountID("accountId")
                .withNombre("My account")
                .withProveedor(provideProovedor())
                .withUsuario(provideUsuario())
                .build();

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(1);
        usuarioEntity.setUsername("javier");
        usuarioEntity.setPassword("javier");
        usuarioEntity.setBorrado(0);

        ProveedorEntity proveedorEntity = new ProveedorEntity();
        proveedorEntity.setId(1);
        proveedorEntity.setName("GoCardless");

        CuentaBancariaEntity entity = new CuentaBancariaEntity();
        entity.setAccountID("accountId");
        entity.setCantidad(123456.00);
        entity.setBorrado(0);
        entity.setIban("ES208054346756565756");
        entity.setFecha(new Date());
        entity.setMoneda("Euro");
        entity.setProveedor(proveedorEntity);
        entity.setUsuario(usuarioEntity);
        entity.setNombre("My account");

        given(iUsuarioJPARepository.findByUsername("javier")).willReturn(Optional.of(usuarioEntity));
        given(iProveedorJPARepository.findByName("GoCardless")).willReturn(Optional.of(proveedorEntity));
        given(cuentaBancariaJPARepository.findByAccountID("accountId")).willReturn(Optional.of(entity));

        cuentaBancariaService.saveNordigen(cuentaBancaria);

        verify(iUsuarioJPARepository).findByUsername("javier");
        verify(iProveedorJPARepository).findByName("GoCardless");
        verify(cuentaBancariaJPARepository).findByAccountID("accountId");
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveWhenCuentaBancariaNotValid() {
        CuentaBancaria cuentaBancaria = CuentaBancaria.builder()
                .withAccountBalance(provideAccountBalance())
                .withAccountDetails(provideAccountDetails())
                .withAccountID("accountId")
                .withNombre("My account")
                .withProveedor(null)
                .withUsuario(null)
                .build();

        cuentaBancariaService.saveNordigen(cuentaBancaria);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveCuentaBancariaWhenUserNotExists() {
        CuentaBancaria cuentaBancaria = CuentaBancaria.builder()
                .withAccountBalance(provideAccountBalance())
                .withAccountDetails(provideAccountDetails())
                .withAccountID("accountId")
                .withNombre("My account")
                .withProveedor(provideProovedor())
                .withUsuario(provideUsuario())
                .build();

        given(iUsuarioJPARepository.findByUsername("javier")).willReturn(Optional.empty());

        cuentaBancariaService.saveNordigen(cuentaBancaria);

        verify(iUsuarioJPARepository).findByUsername("javier");
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveCuentaBancariaWhenProovedorNotExists() {
        CuentaBancaria cuentaBancaria = CuentaBancaria.builder()
                .withAccountBalance(provideAccountBalance())
                .withAccountDetails(provideAccountDetails())
                .withAccountID("accountId")
                .withNombre("My account")
                .withProveedor(provideProovedor())
                .withUsuario(provideUsuario())
                .build();

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(1);
        usuarioEntity.setUsername("javier");
        usuarioEntity.setPassword("javier");
        usuarioEntity.setBorrado(0);

        given(iUsuarioJPARepository.findByUsername("javier")).willReturn(Optional.of(usuarioEntity));
        given(iProveedorJPARepository.findByName("GoCardless")).willReturn(Optional.empty());

        cuentaBancariaService.saveNordigen(cuentaBancaria);

        verify(iUsuarioJPARepository).findByUsername("javier");
        verify(iProveedorJPARepository).findByName("GoCardless");

    }

    @Test(expected = IllegalArgumentException.class)
    public void saveWhenAccountDetailsNotValid() {
        CuentaBancaria cuentaBancaria = CuentaBancaria.builder()
                .withAccountBalance(provideAccountBalance())
                .withAccountDetails(provideAccountDetailsNotValid())
                .withAccountID("accountId")
                .withNombre("My account")
                .withProveedor(provideProovedor())
                .withUsuario(provideUsuario())
                .build();

        cuentaBancariaService.saveNordigen(cuentaBancaria);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveWhenAccountBalanceNotValid() {
        CuentaBancaria cuentaBancaria = CuentaBancaria.builder()
                .withAccountBalance(provideAccountBalanceNotValid())
                .withAccountDetails(provideAccountDetails())
                .withAccountID("accountId")
                .withNombre("My account")
                .withProveedor(provideProovedor())
                .withUsuario(provideUsuario())
                .build();

        cuentaBancariaService.saveNordigen(cuentaBancaria);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveWhenUsuarioNotValid() {
        CuentaBancaria cuentaBancaria = CuentaBancaria.builder()
                .withAccountBalance(provideAccountBalanceNotValid())
                .withAccountDetails(provideAccountDetails())
                .withAccountID("accountId")
                .withNombre("My account")
                .withProveedor(provideProovedor())
                .withUsuario(null)
                .build();

        cuentaBancariaService.saveNordigen(cuentaBancaria);
    }

    @Test
    public void deleteAccount() {
        CuentaBancaria cuentaBancaria = CuentaBancaria.builder()
                .withAccountID("accountId")
                .build();

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(1);
        usuarioEntity.setUsername("javier");
        usuarioEntity.setPassword("javier");
        usuarioEntity.setBorrado(0);

        ProveedorEntity proveedorEntity = new ProveedorEntity();
        proveedorEntity.setId(1);
        proveedorEntity.setName("GoCardless");

        CuentaBancariaEntity entity = new CuentaBancariaEntity();
        entity.setAccountID("accountId");
        entity.setCantidad(123456.00);
        entity.setBorrado(1);
        entity.setIban("ES208054346756565756");
        entity.setFecha(new Date());
        entity.setMoneda("Euro");
        entity.setProveedor(proveedorEntity);
        entity.setUsuario(usuarioEntity);
        entity.setNombre("My account");
        entity.setId(2);

        CuentaBancariaEntity entityNotDeleted = new CuentaBancariaEntity();
        entityNotDeleted.setAccountID("accountId");
        entityNotDeleted.setCantidad(123456.00);
        entityNotDeleted.setBorrado(1);
        entityNotDeleted.setIban("ES208054346756565756");
        entityNotDeleted.setFecha(new Date());
        entityNotDeleted.setMoneda("Euro");
        entityNotDeleted.setProveedor(proveedorEntity);
        entityNotDeleted.setUsuario(usuarioEntity);
        entityNotDeleted.setNombre("My account");
        entityNotDeleted.setId(2);

        given(cuentaBancariaJPARepository.findByAccountID("accountId")).willReturn(Optional.of(entity));
        given(cuentaBancariaJPARepository.save(entityNotDeleted)).willReturn(entityNotDeleted);

        String msg = cuentaBancariaService.deleteCuenta(cuentaBancaria);

        verify(cuentaBancariaJPARepository).findByAccountID("accountId");
        verify(cuentaBancariaJPARepository).save(entityNotDeleted);

        Assert.assertEquals(Messages.CUENTA_BANCARIA_DELETED.getMessage(), msg);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteAccountAccountIdEmpty() {
        CuentaBancaria cuentaBancaria = CuentaBancaria.builder()
                .withAccountID(StringUtils.EMPTY)
                .build();
        cuentaBancariaService.deleteCuenta(cuentaBancaria);
    }

    @Test(expected = NoSuchElementException.class)
    public void deleteAccountNotFound() {
        CuentaBancaria cuentaBancaria = CuentaBancaria.builder()
                .withAccountID("accountId")
                .build();

        given(cuentaBancariaJPARepository.findByAccountID("accountId")).willReturn(Optional.empty());

        cuentaBancariaService.deleteCuenta(cuentaBancaria);

        verify(cuentaBancariaJPARepository).findByAccountID("accountId");
    }

    private AccountBalance provideAccountBalance() {
        BalanceAmount amount = new BalanceAmount(123456.00, "Euro");
        return new AccountBalance(amount, "Balance", new Date());
    }

    private AccountBalance provideAccountBalanceNotValid() {
        return new AccountBalance(null, "Balance", new Date());
    }

    private AccountDetails provideAccountDetails() {
        return new AccountDetails("1234", "ES208054346756565756", "Euro", "Javier", "Javier",
                "MyMoney", "Account");
    }

    private AccountDetails provideAccountDetailsNotValid() {
        return new AccountDetails("1234", null, "Euro", "Javier", "Javier",
                "MyMoney", "Account");
    }

    private ProveedorEntity provideProovedor() {
        ProveedorEntity entity = new ProveedorEntity();
        entity.setId(1);
        entity.setName("GoCardless");

        return entity;
    }

    private static UsuarioEntity provideUsuario() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUsername("javier");
        usuario.setPassword("javier");
        usuario.setBorrado(0);

        return usuario;
    }
}
