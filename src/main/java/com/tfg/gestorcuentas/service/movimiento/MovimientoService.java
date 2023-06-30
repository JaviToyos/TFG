package com.tfg.gestorcuentas.service.movimiento;

import com.tfg.gestorcuentas.data.entity.CuentaBancariaEntity;
import com.tfg.gestorcuentas.data.entity.MovimientoEntity;
import com.tfg.gestorcuentas.data.repository.IMovimientoJPARepository;
import com.tfg.gestorcuentas.service.movimiento.model.Movimiento;
import com.tfg.gestorcuentas.utils.MessageErrors;
import com.tfg.gestorcuentas.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovimientoService implements IMovimientoService {
    private final IMovimientoJPARepository iMovimientoJPARepository;

    @Autowired
    public MovimientoService(IMovimientoJPARepository iMovimientoJPARepository) {
      this.iMovimientoJPARepository = iMovimientoJPARepository;
    }

    @Override
    public String saveNordigen(Movimiento movimiento) {
        if (!validateMovimiento(movimiento.getMovimiento()))
            throw new IllegalArgumentException(MessageErrors.MOVIMIENTO_DATA_EMPTY.getErrorCode());
        if (!validateCuentaBancaria(movimiento.getCuentaBancaria()))
            throw new IllegalArgumentException(MessageErrors.ACCOUNT_DATA_EMPTY.getErrorCode());

        MovimientoEntity movimientoEntity = new MovimientoEntity();
        movimientoEntity.setCuentaBancaria(movimiento.getCuentaBancaria());
        movimientoEntity.setCategoriaEntitySet(movimiento.getMovimiento().getCategoriaEntitySet());
        movimientoEntity.setIdTransaccion(movimiento.getMovimiento().getIdTransaccion());
        movimientoEntity.setCantidad(movimiento.getMovimiento().getCantidad());
        movimientoEntity.setDivisa(movimiento.getMovimiento().getDivisa());
        movimientoEntity.setDestinatario(movimiento.getMovimiento().getDestinatario());
        movimientoEntity.setFecha(movimiento.getMovimiento().getFecha());
        movimientoEntity.setBorrado(0);

        iMovimientoJPARepository.save(movimientoEntity);
        return Messages.MOVIMIENTOS_GUARDADO_OK.getMessage();
    }

    private static boolean validateMovimiento(MovimientoEntity movimiento) {
        return movimiento.getCategoriaEntitySet() != null && movimiento.getIdTransaccion() != null &&
                movimiento.getCantidad() != null && movimiento.getDivisa() != null &&
                movimiento.getDestinatario() != null;
    }

    private static boolean validateCuentaBancaria(CuentaBancariaEntity cuentaBancaria) {
        return cuentaBancaria != null && cuentaBancaria.getUsuario() != null &&
                cuentaBancaria.getProveedor() != null && cuentaBancaria.getNombre() != null;
    }

}
