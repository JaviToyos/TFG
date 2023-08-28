package com.tfg.gestorcuentas.data.repository;

import com.tfg.gestorcuentas.data.entity.CategoriaEntity;
import com.tfg.gestorcuentas.data.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ICategoriaJPARepository extends JpaRepository<CategoriaEntity, Integer> {

    Optional<CategoriaEntity> findByNombreContainingIgnoreCase(String nombre);

    List<CategoriaEntity> findByUsuario_Id(Integer idUsuario);

    List<CategoriaEntity> findByUsuarioAndBorrado(UsuarioEntity usuario, Integer borrado);
}
