package com.tfg.gestorcuentas.service.categoria.model;

import com.tfg.gestorcuentas.data.entity.UsuarioEntity;

import java.util.Objects;

public class Categoria {
    private UsuarioEntity usuario;
    private Integer idCategoria;
    private String nombre;
    private Integer borradoCategoria;

    public Categoria(UsuarioEntity usuario, String nombre) {
        this.usuario = usuario;
        this.nombre = nombre;
    }

    public Categoria(Integer idCategoria, String nombre) {
        this.idCategoria = idCategoria;
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

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public Integer getBorradoCategoria() {
        return borradoCategoria;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return Objects.equals(usuario, categoria.usuario) && Objects.equals(idCategoria, categoria.idCategoria) && Objects.equals(nombre, categoria.nombre) && Objects.equals(borradoCategoria, categoria.borradoCategoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, idCategoria, nombre, borradoCategoria);
    }
}
