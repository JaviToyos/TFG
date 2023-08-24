package com.tfg.gestorcuentas.service.movimiento.model;

public class FindMovimientoRequest {

    private Integer idCuenta;


    public FindMovimientoRequest() {
    }

    public FindMovimientoRequest(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Integer getIdCuenta() {
        return idCuenta;
    }
}
