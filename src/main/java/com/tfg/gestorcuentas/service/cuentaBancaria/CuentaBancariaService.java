package com.tfg.gestorcuentas.service.cuentaBancaria;

import com.mysql.cj.util.StringUtils;
import com.tfg.gestorcuentas.data.entity.CuentaBancariaEntity;
import com.tfg.gestorcuentas.data.entity.ProveedorEntity;
import com.tfg.gestorcuentas.data.entity.UsuarioEntity;
import com.tfg.gestorcuentas.data.repository.ICuentaBancariaJPARepository;
import com.tfg.gestorcuentas.data.repository.IProveedorJPARepository;
import com.tfg.gestorcuentas.data.repository.IUsuarioJPARepository;
import com.tfg.gestorcuentas.service.cuentaBancaria.model.CuentaBancaria;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.balance.AccountBalance;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.details.AccountDetails;
import com.tfg.gestorcuentas.utils.MessageErrors;
import com.tfg.gestorcuentas.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class CuentaBancariaService implements ICuentaBancariaService {

    private final ICuentaBancariaJPARepository iCuentaBancariaJPARepository;
    private final IUsuarioJPARepository iUsuarioJPARepository;
    private final IProveedorJPARepository iProveedorJPARepository;

    @Autowired
    public CuentaBancariaService(ICuentaBancariaJPARepository iCuentaBancariaJPARepository,
                                 IUsuarioJPARepository iUsuarioJPARepository,
                                 IProveedorJPARepository iProveedorJPARepository) {
        this.iCuentaBancariaJPARepository = iCuentaBancariaJPARepository;
        this.iUsuarioJPARepository = iUsuarioJPARepository;
        this.iProveedorJPARepository = iProveedorJPARepository;
    }

    @Override
    public String saveNordigen(CuentaBancaria cuentaBancaria) {
        Integer borradoCuenta = 0;

        if (!validateCuentaBancaria(cuentaBancaria))
            throw new IllegalArgumentException(MessageErrors.ACCOUNT_DATA_EMPTY.getErrorCode());
        if (!validateAccountDetails(cuentaBancaria.getAccountDetails()))
            throw new IllegalArgumentException(MessageErrors.ACCOUNT_DETAILS_EMPTY.getErrorCode());
        if (!validateAccountBalance(cuentaBancaria.getAccountBalance()))
            throw new IllegalArgumentException(MessageErrors.ACCOUNT_BALANCE_EMPTY.getErrorCode());
        if (!validateUsuario(cuentaBancaria.getUsuario()))
            throw new IllegalArgumentException(MessageErrors.USER_DATA_EMPTY.getErrorCode());

        Optional<UsuarioEntity> usuario = iUsuarioJPARepository.findByUsername(cuentaBancaria.getUsuario().getUsername());
        UsuarioEntity usuarioBD = usuario.orElseThrow(() -> new IllegalArgumentException(MessageErrors.USER_NOT_FOUND.getErrorCode()));
        ProveedorEntity proveedorBD = iProveedorJPARepository.findByName(cuentaBancaria.getProveedor().getName()).orElseThrow(() ->
                new IllegalArgumentException(MessageErrors.PROVIDER_NOT_FOUND.getErrorCode()));
        Optional<CuentaBancariaEntity> cuentaBD = iCuentaBancariaJPARepository
                .findByAccountID(cuentaBancaria.getAccountID());

        CuentaBancariaEntity account = new CuentaBancariaEntity();
        if(cuentaBD.isPresent()) {
            if(cuentaBD.get().getBorrado() == 1){
                borradoCuenta = 0;
                account.setId(cuentaBD.get().getId());
            } else {
                throw new IllegalArgumentException(MessageErrors.BANK_ACCOUNT_ALREADY_EXISTS.getErrorCode());
            }
        }

        account.setUsuario(usuarioBD);
        account.setProveedor(proveedorBD);
        account.setAccountID(cuentaBancaria.getAccountID());
        account.setNombre(cuentaBancaria.getNombre());
        account.setIban(cuentaBancaria.getAccountDetails().getIban());
        account.setMoneda(cuentaBancaria.getAccountDetails().getCurrency());
        account.setCantidad(cuentaBancaria.getAccountBalance().getBalanceAmount().getAmount());
        account.setFecha(new Date());
        account.setBorrado(borradoCuenta);

        iCuentaBancariaJPARepository.save(account);
        return Messages.CUENTA_BANCARIA_GUARDADA.getMessage();
    }

    @Override
    public List<CuentaBancariaEntity> findByUsername(String username) {
        if (StringUtils.isNullOrEmpty(username))
            throw new IllegalArgumentException(MessageErrors.USER_NOT_FOUND.getErrorCode());

        Optional<UsuarioEntity> entityUsuario = iUsuarioJPARepository.findByUsername(username);
        UsuarioEntity usuario = entityUsuario.orElseThrow(() -> new NoSuchElementException(MessageErrors.USER_NOT_FOUND.getErrorCode()));

        return iCuentaBancariaJPARepository.findByUsuarioAndBorrado(usuario, 0);
    }

    @Override
    public String deleteCuenta(CuentaBancaria cuentaBancaria) {
        if(StringUtils.isNullOrEmpty(cuentaBancaria.getAccountID())) {
            throw new IllegalArgumentException(MessageErrors.ACCOUNT_DATA_EMPTY.getErrorCode());
        }
        CuentaBancariaEntity cuentaBD = iCuentaBancariaJPARepository.findByAccountID(cuentaBancaria
                .getAccountID()).orElseThrow(() -> new NoSuchElementException(MessageErrors.BANK_ACCOUNT_NOT_FOUND.getErrorCode()));
        cuentaBD.setBorrado(1);
        iCuentaBancariaJPARepository.save(cuentaBD);
        return Messages.CUENTA_BANCARIA_DELETED.getMessage();
    }

    private static boolean validateCuentaBancaria(CuentaBancaria cuentaBancaria) {
        return cuentaBancaria.getAccountID() != null && cuentaBancaria.getUsuario() != null &&
                cuentaBancaria.getProveedor() != null && cuentaBancaria.getNombre() != null &&
                cuentaBancaria.getAccountDetails() != null && cuentaBancaria.getAccountBalance() != null;
    }

    private static boolean validateAccountDetails(AccountDetails details) {
        return details.getResourceId() != null && details.getIban() != null && details.getCurrency() != null;
    }

    private static boolean validateAccountBalance(AccountBalance balance) {
        return balance.getBalanceAmount() != null && balance.getBalanceAmount().getAmount() != null &&
                balance.getReferenceDate() != null && balance.getBalanceAmount().getCurrency() != null;
    }

    private static boolean validateUsuario(UsuarioEntity usuario) {
        return usuario.getUsername() != null;
    }
}
