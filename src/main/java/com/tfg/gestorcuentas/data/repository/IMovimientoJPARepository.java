package com.tfg.gestorcuentas.data.repository;

import com.tfg.gestorcuentas.data.entity.MovimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IMovimientoJPARepository extends JpaRepository<MovimientoEntity, Integer> {

    @Query("SELECT p FROM MovimientoEntity p JOIN FETCH p.categoriaEntitySet WHERE p.cuentaBancaria.id=:id")
    List<MovimientoEntity> findByCuentaBancaria_Id(Integer id);
}
