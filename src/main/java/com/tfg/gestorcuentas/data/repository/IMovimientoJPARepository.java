package com.tfg.gestorcuentas.data.repository;

import com.tfg.gestorcuentas.data.entity.MovimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IMovimientoJPARepository extends JpaRepository<MovimientoEntity, Integer> {

    Optional<MovimientoEntity> findByIdTransaccion(String idTransaccion);

    List<MovimientoEntity> findByCuentaBancaria_Id(Integer id);
}
