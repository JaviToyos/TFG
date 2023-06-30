package com.tfg.gestorcuentas.service.movimiento.model;

import com.tfg.gestorcuentas.data.entity.CuentaBancariaEntity;
import com.tfg.gestorcuentas.data.entity.MovimientoEntity;

public class Movimiento {
    private final CuentaBancariaEntity cuentaBancaria;
    private final MovimientoEntity movimiento;

    public Movimiento(MovimientoEntity movimiento, CuentaBancariaEntity cuentaBancaria) {
        this.movimiento = movimiento;
        this.cuentaBancaria = cuentaBancaria;
    }

    private Movimiento(Builder builder) {
        cuentaBancaria = builder.cuentaBancaria;
        movimiento = builder.movimiento;
    }

    public CuentaBancariaEntity getCuentaBancaria() {
        return cuentaBancaria;
    }

    public MovimientoEntity getMovimiento() {
        return movimiento;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private CuentaBancariaEntity cuentaBancaria;
        private MovimientoEntity movimiento;

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

        public Movimiento build() {
            return new Movimiento(this);
        }
    }
}
