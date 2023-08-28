package com.tfg.gestorcuentas.service.movimiento;

import com.tfg.gestorcuentas.service.movimiento.model.Movimiento;
import com.tfg.gestorcuentas.service.movimiento.model.MovimientoDTO;

import java.util.List;

public interface IMovimientoService {
    String saveNordigen(Movimiento movimiento);
    List<MovimientoDTO> findByIdCuenta(Integer idCuenta);

}
