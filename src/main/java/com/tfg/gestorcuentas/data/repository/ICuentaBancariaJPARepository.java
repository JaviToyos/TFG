package com.tfg.gestorcuentas.data.repository;

import com.tfg.gestorcuentas.data.entity.CuentaBancariaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICuentaBancariaJPARepository extends JpaRepository<CuentaBancariaEntity, String> {
}
