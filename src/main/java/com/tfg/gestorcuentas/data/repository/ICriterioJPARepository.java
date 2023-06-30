package com.tfg.gestorcuentas.data.repository;

import com.tfg.gestorcuentas.data.entity.CriterioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICriterioJPARepository extends JpaRepository<CriterioEntity, String> {
}
