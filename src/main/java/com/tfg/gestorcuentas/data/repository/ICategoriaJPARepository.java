package com.tfg.gestorcuentas.data.repository;

import com.tfg.gestorcuentas.data.entity.CategoriaEntity;
import com.tfg.gestorcuentas.data.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICategoriaJPARepository extends JpaRepository<CategoriaEntity, Integer> {
    List<CategoriaEntity> findByUsuario_Id(Integer idUsuario);

    List<CategoriaEntity> findByUsuarioAndBorrado(UsuarioEntity usuario, Integer borrado);
}
