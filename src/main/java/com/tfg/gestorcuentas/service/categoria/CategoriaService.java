package com.tfg.gestorcuentas.service.categoria;

import com.tfg.gestorcuentas.data.entity.CategoriaEntity;
import com.tfg.gestorcuentas.data.entity.CriterioEntity;
import com.tfg.gestorcuentas.data.entity.UsuarioEntity;
import com.tfg.gestorcuentas.data.repository.ICategoriaJPARepository;
import com.tfg.gestorcuentas.data.repository.ICriterioJPARepository;
import com.tfg.gestorcuentas.data.repository.IUsuarioJPARepository;
import com.tfg.gestorcuentas.service.categoria.model.Categoria;
import com.tfg.gestorcuentas.utils.MessageErrors;
import com.tfg.gestorcuentas.utils.Messages;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CategoriaService implements ICategoriaService {
    private final ICategoriaJPARepository iCategoriaJPARepository;
    private final ICriterioJPARepository iCriterioJPARepository;
    private final IUsuarioJPARepository iUsuarioJPARepository;

    @Autowired
    public CategoriaService(ICategoriaJPARepository iCategoriaJPARepository, ICriterioJPARepository iCriterioJPARepository, IUsuarioJPARepository iUsuarioJPARepository) {
        this.iCategoriaJPARepository = iCategoriaJPARepository;
        this.iCriterioJPARepository = iCriterioJPARepository;
        this.iUsuarioJPARepository = iUsuarioJPARepository;
    }

    @Override
    public String save(Categoria categoria) {
        if (!validateCategoria(categoria))
            throw new IllegalArgumentException(MessageErrors.CATEGORIA_EMPTY.getErrorCode());

        Optional<CategoriaEntity> categoriaBD = iCategoriaJPARepository.findByNombreContainingIgnoreCase(categoria.getNombre());
        Optional<UsuarioEntity> usuarioOptional = iUsuarioJPARepository.findByUsername(categoria.getUsuario().getUsername());
        UsuarioEntity usuarioEntity = usuarioOptional.orElseThrow(IllegalArgumentException::new);

        if(categoriaBD.isPresent()) {
            if(categoriaBD.get().getBorrado() == 1) {
                categoriaBD.get().setBorrado(0);
                iCategoriaJPARepository.save(categoriaBD.get());
            }else{
                throw new IllegalStateException(MessageErrors.CATEGORIA_ALREADY_EXISTS.getErrorCode());
            }
        }else {
            CategoriaEntity categoriaEntity = new CategoriaEntity();
            categoriaEntity.setUsuario(usuarioEntity);
            categoriaEntity.setNombre(categoria.getNombre());
            categoriaEntity.setBorrado(0);
            iCategoriaJPARepository.save(categoriaEntity);
        }
        return Messages.CATEGORIA_GUARDADA.getMessage();
    }

    @Override
    public String modify(Categoria categoria) {
        if (!validateIdCategoria(categoria.getIdCategoria()) || !validateCategoria(categoria))
            throw new IllegalArgumentException(MessageErrors.CATEGORIA_EMPTY.getErrorCode());

        Optional<CategoriaEntity> categoriaBD = iCategoriaJPARepository.findById(categoria.getIdCategoria());

        if (categoriaBD.isEmpty()) {
            throw new IllegalStateException(MessageErrors.CATEGORIA_NOT_FOUND.getErrorCode());
        }

        categoriaBD.get().setNombre(categoria.getNombre());

        iCategoriaJPARepository.save(categoriaBD.get());

        return Messages.CATEGORIA_MODIFIED.getMessage();
    }


    @Override
    public List<CategoriaEntity> findByUsername(String username) {
        if (!StringUtils.isNotBlank(username))
            throw new IllegalArgumentException(MessageErrors.USER_NOT_FOUND.getErrorCode());

        Optional<UsuarioEntity> entityUsuario = iUsuarioJPARepository.findByUsername(username);
        UsuarioEntity usuario = entityUsuario.orElseThrow(() -> new NoSuchElementException(MessageErrors.USER_NOT_FOUND.getErrorCode()));
        return iCategoriaJPARepository.findByUsuarioAndBorrado(usuario, 0);
    }

    @Override
    public String delete(Integer idCategoria) {
        if (idCategoria == null)
            throw new IllegalArgumentException(MessageErrors.ID_CATEGORIA_NULL.getErrorCode());

        Optional<CategoriaEntity> entityCategoria = iCategoriaJPARepository.findById(idCategoria);
        CategoriaEntity categoriaEntity = entityCategoria.orElseThrow(() -> new NoSuchElementException(MessageErrors.CATEGORIA_NOT_FOUND.getErrorCode()));


        List<CriterioEntity> criterioEntityList = iCriterioJPARepository.findByCategoria_IdAndBorrado(idCategoria, 0);

        criterioEntityList.forEach(criterio -> {
            criterio.setBorrado(1);
            iCriterioJPARepository.save(criterio);
        });

        categoriaEntity.setBorrado(1);
        iCategoriaJPARepository.save(categoriaEntity);
        return Messages.CATEGORIA_MODIFIED.getMessage();

    }


    private static boolean validateIdCategoria(Integer idCategoria) {
        return idCategoria != null;
    }

    private static boolean validateCategoria(Categoria categoria) {
        return StringUtils.isNotBlank(categoria.getNombre());
    }



}
