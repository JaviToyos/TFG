package com.tfg.gestorcuentas.data.repository;

import com.tfg.gestorcuentas.data.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsuarioJPARepository extends JpaRepository<UsuarioEntity, String> {
    Optional<UsuarioEntity> findByUsername(String username);

}
