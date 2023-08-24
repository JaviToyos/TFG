package com.tfg.gestorcuentas.service.datosPersonales;

import com.mysql.cj.util.StringUtils;
import com.tfg.gestorcuentas.data.entity.PersonaEntity;
import com.tfg.gestorcuentas.data.entity.UsuarioEntity;
import com.tfg.gestorcuentas.data.repository.IPersonaJPARepository;
import com.tfg.gestorcuentas.data.repository.IUsuarioJPARepository;
import com.tfg.gestorcuentas.service.datosPersonales.model.DatosPersonales;
import com.tfg.gestorcuentas.utils.MessageErrors;
import com.tfg.gestorcuentas.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DatosPersonalesService implements IDatosPersonalesService {
    private final IUsuarioJPARepository iUsuarioJPARepository;

    private final IPersonaJPARepository iPersonaJPARepository;

    @Autowired
    public DatosPersonalesService(IPersonaJPARepository iPersonaJPARepository, IUsuarioJPARepository iUsuarioJPARepository) {
        this.iPersonaJPARepository = iPersonaJPARepository;
        this.iUsuarioJPARepository = iUsuarioJPARepository;
    }

    @Override
    public DatosPersonales findByUsername(String username) throws IllegalArgumentException {
        if (StringUtils.isNullOrEmpty(username))
            throw new IllegalArgumentException(MessageErrors.USER_NOT_FOUND.getErrorCode());

        Optional<UsuarioEntity> entityUsuario = iUsuarioJPARepository.findByUsername(username);
        UsuarioEntity usuario = entityUsuario.orElseThrow(() -> new NoSuchElementException(MessageErrors.USER_NOT_FOUND.getErrorCode()));

        if(validatePersona(usuario.getPersona()) && validateUsuario(usuario))
           return DatosPersonales.builder().withUsuario(usuario).withPersona(usuario.getPersona()).build();

        return null;
    }

    @Override
    public String updateDatosPersonales(DatosPersonales datosPersonales) throws NoSuchElementException, IllegalArgumentException {
        if (!validateUsuario(datosPersonales.getUsuario()) || !validatePersona(datosPersonales.getPersona()))
            throw new IllegalArgumentException(MessageErrors.PESONAL_DATA_EMPTY.getErrorCode());

        Optional<UsuarioEntity> entityUsuario = iUsuarioJPARepository.findByUsername(datosPersonales.getUsuario().getUsername());
        UsuarioEntity usuario = entityUsuario.orElseThrow(() -> new NoSuchElementException(MessageErrors.USER_NOT_FOUND.getErrorCode()));

        usuario.setPassword(datosPersonales.getUsuario().getPassword());
        usuario.setPersona(datosPersonales.getPersona());

        UsuarioEntity usuarioGuardado = iUsuarioJPARepository.save(usuario);
        usuario.getPersona().setUsuario(usuarioGuardado);
        iPersonaJPARepository.save(usuario.getPersona());

        return Messages.PERSONAL_DATA_MODIFIED.getMessage();
    }

    private static boolean validateUsuario(UsuarioEntity usuario) {
        return usuario.getUsername() != null && usuario.getPassword() != null;
    }

    private static boolean validatePersona(PersonaEntity persona) {
        return persona.getNombre() != null && persona.getApellidos() != null &&
                persona.getDni() != null && persona.getEmail() != null;
    }
}