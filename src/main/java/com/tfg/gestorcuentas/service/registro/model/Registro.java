package com.tfg.gestorcuentas.service.registro.model;

import com.tfg.gestorcuentas.data.entity.PersonaEntity;
import com.tfg.gestorcuentas.data.entity.UsuarioEntity;

public class Registro {

    private final PersonaEntity persona;
    private final UsuarioEntity usuario;

    public Registro(PersonaEntity persona, UsuarioEntity usuario) {
        this.persona = persona;
        this.usuario = usuario;
    }

    public PersonaEntity getPersona() {
        return persona;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    private Registro(Builder builder) {
        persona = builder.persona;
        usuario = builder.usuario;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private PersonaEntity persona;
        private UsuarioEntity usuario;

        private Builder() {
        }

        public Builder withPersona(PersonaEntity persona) {
            this.persona = persona;
            return this;
        }

        public Builder withUsuario(UsuarioEntity usuario) {
            this.usuario = usuario;
            return this;
        }

        public Registro build() {
            return new Registro(this);
        }
    }
}
