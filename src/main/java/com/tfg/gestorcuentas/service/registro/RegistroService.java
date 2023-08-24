package com.tfg.gestorcuentas.service.registro;

import com.tfg.gestorcuentas.data.entity.PersonaEntity;
import com.tfg.gestorcuentas.data.entity.UsuarioEntity;
import com.tfg.gestorcuentas.data.repository.IPersonaJPARepository;
import com.tfg.gestorcuentas.data.repository.IUsuarioJPARepository;
import com.tfg.gestorcuentas.service.registro.model.Registro;
import com.tfg.gestorcuentas.utils.MessageErrors;
import com.tfg.gestorcuentas.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroService implements IRegistroService {

    private final IPersonaJPARepository iPersonaJPARepository;
    private final IUsuarioJPARepository iUsuarioJPARepository;

    @Autowired
    public RegistroService(IPersonaJPARepository iPersonaJPARepository, IUsuarioJPARepository iUsuarioJPARepository) {
        this.iPersonaJPARepository = iPersonaJPARepository;
        this.iUsuarioJPARepository = iUsuarioJPARepository;
    }

    @Override
    public String registro(Registro registro) throws IllegalArgumentException {
        PersonaEntity persona = registro.getPersona();
        persona.setDni(persona.getDni().toUpperCase());
        if (validatePersona(persona)) {
            if (persona.getBorrado() == null) persona.setBorrado(0);
            if (iPersonaJPARepository.findByDni(persona.getDni()).isPresent())
                throw new IllegalArgumentException(MessageErrors.DNI_FOUND.getErrorCode());
        } else
            throw new IllegalArgumentException(MessageErrors.ANY_PROPERTY_NULL.getErrorCode());

        UsuarioEntity usuario = registro.getUsuario();
        if (validateUsuario(usuario)) {
            if (usuario.getBorrado() == null) usuario.setBorrado(0);
            if (iUsuarioJPARepository.findByUsername(usuario.getUsername()).isPresent())
                throw new IllegalArgumentException(MessageErrors.USERNAME_FOUND.getErrorCode());
        } else
            throw new IllegalArgumentException(MessageErrors.ANY_PROPERTY_NULL.getErrorCode());

        UsuarioEntity usuarioGuardado = iUsuarioJPARepository.save(usuario);
        persona.setUsuario(usuarioGuardado);
        iPersonaJPARepository.save(persona);

        return Messages.GUARDADO_OK.getMessage();
    }

    private static boolean validateUsuario(UsuarioEntity usuario) {
        return usuario.getUsername() != null && usuario.getPassword() != null;
    }

    private static boolean validatePersona(PersonaEntity persona) {
        return persona.getNombre() != null && persona.getApellidos() != null &&
                persona.getDni() != null && persona.getEmail() != null;
    }
}
