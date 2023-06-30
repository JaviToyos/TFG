package com.tfg.gestorcuentas.service.registro;

import com.tfg.gestorcuentas.service.registro.model.Registro;

public interface IRegistroService {
    String registro(final Registro registro) throws IllegalArgumentException;
}
