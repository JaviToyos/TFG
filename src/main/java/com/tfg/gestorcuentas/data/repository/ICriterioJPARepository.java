package com.tfg.gestorcuentas.data.repository;

import com.tfg.gestorcuentas.data.entity.CriterioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ICriterioJPARepository extends JpaRepository<CriterioEntity, Integer> {
    Optional<CriterioEntity> findById(Integer id);
    Optional<CriterioEntity> findByNombreContainingIgnoreCase(String nombre);
    List<CriterioEntity> findByCategoria_IdAndBorrado(Integer idCategoria, Integer borrado);
}
