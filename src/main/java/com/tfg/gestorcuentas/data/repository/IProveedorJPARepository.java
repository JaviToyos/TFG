package com.tfg.gestorcuentas.data.repository;

import com.tfg.gestorcuentas.data.entity.ProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IProveedorJPARepository extends JpaRepository<ProveedorEntity, Integer> {
    Optional<ProveedorEntity> findByName(String name);
}
