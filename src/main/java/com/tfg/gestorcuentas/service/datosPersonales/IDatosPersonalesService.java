package com.tfg.gestorcuentas.service.datosPersonales;

import com.tfg.gestorcuentas.service.datosPersonales.model.DatosPersonales;

public interface IDatosPersonalesService {

    String updateDatosPersonales(final DatosPersonales datosPersonales) throws IllegalArgumentException;
}
