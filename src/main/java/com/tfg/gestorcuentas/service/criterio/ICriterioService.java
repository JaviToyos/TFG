package com.tfg.gestorcuentas.service.criterio;

import com.tfg.gestorcuentas.data.entity.CriterioEntity;
import com.tfg.gestorcuentas.service.criterio.model.Criterio;

import java.util.List;

public interface ICriterioService {
    String save(Criterio criterio);

    String modify(CriterioEntity criterio);

    List<CriterioEntity> findByCategoria(Integer idCategoria);

    String delete(Integer idCriterio);
}
