package com.tfg.gestorcuentas.data.repository;

import com.tfg.gestorcuentas.data.entity.PersonaEntity;
import com.tfg.gestorcuentas.data.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPersonaJPARepository extends JpaRepository<PersonaEntity, Integer> {
    Optional<PersonaEntity> findByDni(String dni);

    Optional<PersonaEntity> findByUsuario(UsuarioEntity usuario);

}
