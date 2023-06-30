package com.tfg.gestorcuentas.data.repository;

import com.tfg.gestorcuentas.data.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaJPARepository extends JpaRepository<CategoriaEntity, String> {
}
