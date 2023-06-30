package com.tfg.gestorcuentas.service.categoria;

import com.tfg.gestorcuentas.data.entity.CategoriaEntity;
import com.tfg.gestorcuentas.data.entity.UsuarioEntity;
import com.tfg.gestorcuentas.data.repository.ICategoriaJPARepository;
import com.tfg.gestorcuentas.data.repository.IMovimientoJPARepository;
import com.tfg.gestorcuentas.data.repository.IUsuarioJPARepository;
import com.tfg.gestorcuentas.service.categoria.model.Categoria;
import com.tfg.gestorcuentas.utils.MessageErrors;
import com.tfg.gestorcuentas.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService implements ICategoriaService {
    private final IUsuarioJPARepository iUsuarioJPARepository;
    private final IMovimientoJPARepository iMovimientoJPARepository;

    private final ICategoriaJPARepository iCategoriaJPARepository;

    @Autowired
    public CategoriaService(IMovimientoJPARepository iMovimientoJPARepository, IUsuarioJPARepository iUsuarioJPARepository, ICategoriaJPARepository iCategoriaJPARepository) {
        this.iUsuarioJPARepository = iUsuarioJPARepository;
        this.iMovimientoJPARepository = iMovimientoJPARepository;
        this.iCategoriaJPARepository = iCategoriaJPARepository;
    }

    @Override
    public String save(Categoria categoria) {
        UsuarioEntity usuario = categoria.getUsuario();
        if (!validateUsuario(usuario))
            throw new IllegalArgumentException(MessageErrors.USER_DATA_EMPTY.getErrorCode());

        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setUsuario(usuario);
        categoriaEntity.setNombre(categoria.getNombre());
        categoriaEntity.setBorrado(0);

        iCategoriaJPARepository.save(categoriaEntity);
        return Messages.CATEGORIA_GUARDADA.getMessage();
    }

    @Override
    public String modify(Categoria categoria) {
        UsuarioEntity usuario = categoria.getUsuario();
        if (!validateUsuario(usuario))
            throw new IllegalArgumentException(MessageErrors.USER_DATA_EMPTY.getErrorCode());
            //TODO quiero utilizar un metodo update de JPA pero quiá tengo que implementarlo yo
        return Messages.CATEGORIA_MODIFIED.getMessage();
    }

    private static boolean validateUsuario(UsuarioEntity usuario) {
        return usuario.getUsername() != null && usuario.getPassword() != null;
    }

}
