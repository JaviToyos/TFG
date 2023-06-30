package com.tfg.gestorcuentas.service.categoria.model;

import com.tfg.gestorcuentas.data.entity.UsuarioEntity;

public class Categoria {
    private final UsuarioEntity usuario;

    private final String nombre;


    public Categoria(UsuarioEntity usuario, String nombre) {
        this.usuario = usuario;
        this.nombre = nombre;
    }

    private Categoria(Builder builder) {
        usuario = builder.usuario;
        nombre = builder.nombre;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private UsuarioEntity usuario;
        private String nombre;

        private Builder() {
        }

        public Builder withUsuario(UsuarioEntity val) {
            usuario = val;
            return this;
        }

        public Builder withNombre(String val) {
            nombre = val;
            return this;
        }

        public Categoria build() {
            return new Categoria(this);
        }
    }
}
