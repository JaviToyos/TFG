package com.tfg.gestorcuentas.data.repository;

import com.tfg.gestorcuentas.data.entity.ParametroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IParametroJPARepository extends JpaRepository<ParametroEntity, Integer> {
    List<ParametroEntity> findByProveedorId(Integer idProveedor);
}
