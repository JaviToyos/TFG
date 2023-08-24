package com.tfg.gestorcuentas.service.movimiento.model;

import com.tfg.gestorcuentas.data.entity.CategoriaEntity;
import com.tfg.gestorcuentas.data.entity.CuentaBancariaEntity;
import com.tfg.gestorcuentas.data.entity.MovimientoEntity;

import java.util.List;

public class Movimiento {
    private final CuentaBancariaEntity cuentaBancaria;
    private final MovimientoEntity movimiento;
    private final List<CategoriaEntity> categorias;

    public Movimiento(CuentaBancariaEntity cuentaBancaria, MovimientoEntity movimiento, List<CategoriaEntity> categorias) {
        this.cuentaBancaria = cuentaBancaria;
        this.movimiento = movimiento;
        this.categorias = categorias;
    }

    private Movimiento(Builder builder) {
        cuentaBancaria = builder.cuentaBancaria;
        movimiento = builder.movimiento;
        categorias = builder.categorias;
    }

    public CuentaBancariaEntity getCuentaBancaria() {
        return cuentaBancaria;
    }

    public MovimientoEntity getMovimiento() {
        return movimiento;
    }

    public List<CategoriaEntity> getCategorias() {
        return categorias;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private CuentaBancariaEntity cuentaBancaria;
        private MovimientoEntity movimiento;
        private List<CategoriaEntity> categorias;

        private Builder() {
        }

        public Builder withCuentaBancaria(CuentaBancariaEntity val) {
            cuentaBancaria = val;
            return this;
        }

        public Builder withMovimiento(MovimientoEntity val) {
            movimiento = val;
            return this;
        }

        public Builder withCategorias(List<CategoriaEntity> val) {
            categorias = val;
            return this;
        }

        public Movimiento build() {
            return new Movimiento(this);
        }
    }
}
