package com.tfg.gestorcuentas.service.criterio;

import com.tfg.gestorcuentas.data.entity.CategoriaEntity;
import com.tfg.gestorcuentas.data.entity.CriterioEntity;
import com.tfg.gestorcuentas.data.entity.UsuarioEntity;
import com.tfg.gestorcuentas.data.repository.ICriterioJPARepository;
import com.tfg.gestorcuentas.service.criterio.model.Criterio;
import com.tfg.gestorcuentas.utils.MessageErrors;
import com.tfg.gestorcuentas.utils.Messages;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CriterioService implements ICriterioService {

    private final ICriterioJPARepository iCriterioJPARepository;

    @Autowired
    public CriterioService(ICriterioJPARepository iCriterioJPARepository) {
        this.iCriterioJPARepository = iCriterioJPARepository;
    }

    @Override
    public String save(Criterio criterio) {
        CategoriaEntity categoria = criterio.getCategoria();
        if (!validateCategoria(categoria))
            throw new IllegalArgumentException(MessageErrors.CATEGORIA_EMPTY.getErrorCode());

        CriterioEntity criterioEntity = new CriterioEntity();
        criterioEntity.setCategoria(criterio.getCategoria());
        criterioEntity.setNombre(criterio.getNombre());
        criterioEntity.setBorrado(0);

        iCriterioJPARepository.save(criterioEntity);
        return Messages.CRITERIO_MODIFIED.getMessage();
    }

    private static boolean validateCategoria(CategoriaEntity categoria) {
        return categoria.getUsuario() != null && categoria.getNombre() != null;
    }
}
