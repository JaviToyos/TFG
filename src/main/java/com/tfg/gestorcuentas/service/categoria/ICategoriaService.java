package com.tfg.gestorcuentas.service.categoria;

import com.tfg.gestorcuentas.service.categoria.model.Categoria;

public interface ICategoriaService {
    String save(Categoria categoria);
    String modify(Categoria categoria);
}
