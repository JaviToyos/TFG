package com.tfg.gestorcuentas.service.cuentaBancaria;

import com.tfg.gestorcuentas.data.entity.CuentaBancariaEntity;
import com.tfg.gestorcuentas.data.entity.UsuarioEntity;
import com.tfg.gestorcuentas.data.repository.ICuentaBancariaJPARepository;
import com.tfg.gestorcuentas.data.repository.IMovimientoJPARepository;
import com.tfg.gestorcuentas.data.repository.IUsuarioJPARepository;
import com.tfg.gestorcuentas.service.cuentaBancaria.model.CuentaBancaria;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.balance.AccountBalance;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.details.AccountDetails;
import com.tfg.gestorcuentas.utils.MessageErrors;
import com.tfg.gestorcuentas.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CuentaBancariaService implements ICuentaBancariaService {

    private final IUsuarioJPARepository iUsuarioJPARepository;
    private final ICuentaBancariaJPARepository iCuentaBancariaJPARepository;
    private final IMovimientoJPARepository iMovimientoJPARepository;

    @Autowired
    public CuentaBancariaService(IMovimientoJPARepository iMovimientoJPARepository, IUsuarioJPARepository iUsuarioJPARepository, ICuentaBancariaJPARepository iCuentaBancariaJPARepository) {
        this.iUsuarioJPARepository = iUsuarioJPARepository;
        this.iMovimientoJPARepository = iMovimientoJPARepository;
        this.iCuentaBancariaJPARepository = iCuentaBancariaJPARepository;
    }

    @Override
    public String saveNordigen(CuentaBancaria cuentaBancaria) {
        if (!validateCuentaBancaria(cuentaBancaria))
            throw new IllegalArgumentException(MessageErrors.ACCOUNT_DATA_EMPTY.getErrorCode());
        if (!validateAccountDetails(cuentaBancaria.getAccountDetails()))
            throw new IllegalArgumentException(MessageErrors.ACCOUNT_DETAILS_EMPTY.getErrorCode());
        if (!validateAccountBalance(cuentaBancaria.getAccountBalance()))
            throw new IllegalArgumentException(MessageErrors.ACCOUNT_BALANCE_EMPTY.getErrorCode());
        if (!validateUsuario(cuentaBancaria.getUsuario()))
            throw new IllegalArgumentException(MessageErrors.USER_DATA_EMPTY.getErrorCode());

        CuentaBancariaEntity account = new CuentaBancariaEntity();
        account.setUsuario(cuentaBancaria.getUsuario());
        account.setProveedor(cuentaBancaria.getProveedor());
        account.setAccountID(cuentaBancaria.getAccountID());
        account.setNombre(cuentaBancaria.getNombre());
        account.setIban(cuentaBancaria.getAccountDetails().getIban());
        account.setMoneda(cuentaBancaria.getAccountDetails().getCurrency());
        account.setCantidad(cuentaBancaria.getAccountBalance().getBalanceAmount().getAmount());
        account.setFecha(cuentaBancaria.getAccountBalance().getReferenceDate());
        account.setBorrado(0);

        iCuentaBancariaJPARepository.save(account);
        return Messages.CUENTA_BANCARIA_GUARDADA.getMessage();
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
        return usuario.getUsername() != null && usuario.getPassword() != null;
    }


}
