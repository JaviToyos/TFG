package com.tfg.gestorcuentas.data.repository;

import com.tfg.gestorcuentas.data.entity.MovimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMovimientoJPARepository extends JpaRepository<MovimientoEntity, String> {
}
