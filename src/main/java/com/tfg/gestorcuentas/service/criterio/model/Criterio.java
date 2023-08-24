package com.tfg.gestorcuentas.service.criterio.model;

import com.tfg.gestorcuentas.data.entity.CategoriaEntity;

public class Criterio {
    private final CategoriaEntity categoria;
    private final String nombre;

    public Criterio( CategoriaEntity categoria, String nombre) {
        this.categoria = categoria;
        this.nombre = nombre;
    }

    private Criterio(Builder builder) {
        categoria = builder.categoria;
        nombre = builder.nombre;
    }

    public CategoriaEntity getCategoria() {
        return categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private CategoriaEntity categoria;
        private String nombre;

        private Builder() {
        }

        public Builder withCategoria(CategoriaEntity val) {
            categoria = val;
            return this;
        }

        public Builder withNombre(String val) {
            nombre = val;
            return this;
        }

        public Criterio build() {
            return new Criterio(this);
        }
    }
}

