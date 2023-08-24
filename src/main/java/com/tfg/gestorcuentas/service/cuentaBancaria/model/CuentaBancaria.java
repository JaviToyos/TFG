package com.tfg.gestorcuentas.service.cuentaBancaria.model;

import com.tfg.gestorcuentas.data.entity.ProveedorEntity;
import com.tfg.gestorcuentas.data.entity.UsuarioEntity;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.balance.AccountBalance;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.details.AccountDetails;

import java.util.Objects;

public class CuentaBancaria {
    private final String accountID;
    private final UsuarioEntity usuario;
    private final ProveedorEntity proveedor;
    private final String nombre;
    private final AccountDetails accountDetails;
    private final AccountBalance accountBalance;

    public CuentaBancaria(String accountID, UsuarioEntity usuario, ProveedorEntity proveedor, String nombre, AccountDetails accountDetails, AccountBalance accountBalance) {
        this.accountID = accountID;
        this.usuario = usuario;
        this.proveedor = proveedor;
        this.nombre = nombre;
        this.accountDetails = accountDetails;
        this.accountBalance = accountBalance;
    }

    private CuentaBancaria(Builder builder) {
        accountID = builder.accountID;
        usuario = builder.usuario;
        proveedor = builder.proveedor;
        nombre = builder.nombre;
        accountDetails = builder.accountDetails;
        accountBalance = builder.accountBalance;
    }

    public String getAccountID() {
        return accountID;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public ProveedorEntity getProveedor() {
        return proveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public AccountDetails getAccountDetails() {
        return accountDetails;
    }

    public AccountBalance getAccountBalance() {
        return accountBalance;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String accountID;
        private UsuarioEntity usuario;
        private ProveedorEntity proveedor;
        private String nombre;
        private AccountDetails accountDetails;
        private AccountBalance accountBalance;

        private Builder() {
        }

        public Builder withAccountID(String val) {
            accountID = val;
            return this;
        }

        public Builder withUsuario(UsuarioEntity val) {
            usuario = val;
            return this;
        }

        public Builder withProveedor(ProveedorEntity val) {
            proveedor = val;
            return this;
        }

        public Builder withNombre(String val) {
            nombre = val;
            return this;
        }

        public Builder withAccountDetails(AccountDetails val) {
            accountDetails = val;
            return this;
        }

        public Builder withAccountBalance(AccountBalance val) {
            accountBalance = val;
            return this;
        }

        public CuentaBancaria build() {
            return new CuentaBancaria(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CuentaBancaria that = (CuentaBancaria) o;
        return Objects.equals(accountID, that.accountID) && Objects.equals(usuario, that.usuario) && Objects.equals(proveedor, that.proveedor) && Objects.equals(nombre, that.nombre) && Objects.equals(accountDetails, that.accountDetails) && Objects.equals(accountBalance, that.accountBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountID, usuario, proveedor, nombre, accountDetails, accountBalance);
    }
}
