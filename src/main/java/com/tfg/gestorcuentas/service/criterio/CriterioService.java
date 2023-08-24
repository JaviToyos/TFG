package com.tfg.gestorcuentas.service.criterio;

import com.tfg.gestorcuentas.data.entity.CategoriaEntity;
import com.tfg.gestorcuentas.data.entity.CriterioEntity;
import com.tfg.gestorcuentas.data.repository.ICategoriaJPARepository;
import com.tfg.gestorcuentas.data.repository.ICriterioJPARepository;
import com.tfg.gestorcuentas.service.criterio.model.Criterio;
import com.tfg.gestorcuentas.utils.MessageErrors;
import com.tfg.gestorcuentas.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CriterioService implements ICriterioService {

    private final ICriterioJPARepository iCriterioJPARepository;
    private final ICategoriaJPARepository iCategoriaJPARepository;

    @Autowired
    public CriterioService(ICriterioJPARepository iCriterioJPARepository,
                           ICategoriaJPARepository iCategoriaJPARepository) {
        this.iCriterioJPARepository = iCriterioJPARepository;
        this.iCategoriaJPARepository = iCategoriaJPARepository;
    }

    @Override
    public String save(Criterio criterio) {
        CategoriaEntity categoria = criterio.getCategoria();
        if (!validateCategoria(categoria))
            throw new IllegalArgumentException(MessageErrors.CATEGORIA_EMPTY.getErrorCode());

        Optional<CriterioEntity> criterioBD = iCriterioJPARepository.findByNombreContainingIgnoreCase(criterio.getNombre());

        if(criterioBD.isPresent()) {
            if(criterioBD.get().getBorrado() == 1) {
                criterioBD.get().setBorrado(0);
                iCriterioJPARepository.save(criterioBD.get());
            } else {
                throw new IllegalArgumentException(MessageErrors.CRITERIO_ALREADY_EXISTS.getErrorCode());
            }

        } else {
            Optional<CategoriaEntity> categoriaBD = iCategoriaJPARepository.findById(criterio.getCategoria().getId());

            if(categoriaBD.isEmpty())
                throw new IllegalArgumentException(MessageErrors.CATEGORIA_NOT_FOUND.getErrorCode());

            CriterioEntity criterioEntity = new CriterioEntity();
            criterioEntity.setCategoria(criterio.getCategoria());
            criterioEntity.setNombre(criterio.getNombre());
            criterioEntity.setBorrado(0);

            iCriterioJPARepository.save(criterioEntity);
        }

        return Messages.CRITERIO_SAVED.getMessage();
    }

    @Override
    public String modify(CriterioEntity criterio) {
        if(!validateCriterio(criterio)) {
            throw new IllegalArgumentException(MessageErrors.ID_CRITERIO_NULL.getErrorCode());
        }

        CriterioEntity criterioBD = iCriterioJPARepository.findById(criterio.getId())
                .orElseThrow(() -> new IllegalStateException(MessageErrors.CRITERIO_NOT_FOUND.getErrorCode()));

        criterioBD.setNombre(criterio.getNombre());


        iCriterioJPARepository.save(criterioBD);
        return Messages.CRITERIO_MODIFIED.getMessage();
    }

    @Override
    public List<CriterioEntity> findByCategoria(Integer idCategoria) throws IllegalArgumentException {
        if (idCategoria == null)
            throw new IllegalArgumentException(MessageErrors.CATEGORIA_NOT_FOUND.getErrorCode());

        return iCriterioJPARepository.findByCategoria_IdAndBorrado(idCategoria, 0);
    }

    public String delete(Integer idCriterio) {
        if (idCriterio == null)
            throw new IllegalArgumentException(MessageErrors.ID_CRITERIO_NULL.getErrorCode());

        Optional<CriterioEntity> entityCriterio = iCriterioJPARepository.findById(idCriterio);
        CriterioEntity criterioEntity = entityCriterio.orElseThrow(() -> new NoSuchElementException(MessageErrors.CRITERIO_NOT_FOUND.getErrorCode()));

        if (validateCriterio(criterioEntity)) {
            criterioEntity.setBorrado(1);
            iCriterioJPARepository.save(criterioEntity);
            return Messages.CRITERIO_MODIFIED.getMessage();
        }
        return MessageErrors.CRITERIO_NOT_FOUND.getErrorCode();
    }


    private static boolean validateCategoria(CategoriaEntity categoria) {
        return categoria.getId() != null;
    }

    private static boolean validateCriterio(CriterioEntity criterio) {
        return criterio.getId() != null;
    }

}
