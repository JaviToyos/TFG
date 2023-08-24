package com.tfg.gestorcuentas.data.repository;

import com.tfg.gestorcuentas.data.entity.CuentaBancariaEntity;
import com.tfg.gestorcuentas.data.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ICuentaBancariaJPARepository extends JpaRepository<CuentaBancariaEntity, String> {
        List<CuentaBancariaEntity> findByUsuarioAndBorrado(UsuarioEntity usuario, Integer borrado);

        Optional<CuentaBancariaEntity> findByAccountID(String accountId);
}
