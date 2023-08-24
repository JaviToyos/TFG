package com.tfg.gestorcuentas.service.cuentaBancaria;

import com.tfg.gestorcuentas.data.entity.CuentaBancariaEntity;
import com.tfg.gestorcuentas.service.cuentaBancaria.model.CuentaBancaria;

import java.util.List;

public interface ICuentaBancariaService {
    String saveNordigen (CuentaBancaria cuentaBancaria);

    List<CuentaBancariaEntity> findByUsername(String username);

    String deleteCuenta(CuentaBancaria cuentaBancaria);
}
