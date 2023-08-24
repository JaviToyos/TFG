package com.tfg.gestorcuentas.service.categoria;

import com.tfg.gestorcuentas.data.entity.CategoriaEntity;
import com.tfg.gestorcuentas.service.categoria.model.Categoria;

import java.util.List;

public interface ICategoriaService {
    String save(Categoria categoria);

    String modify(Categoria categoria);

    String delete(Integer idCategoria);

    List<CategoriaEntity> findByUsername(String username);
}
