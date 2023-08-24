package com.tfg.gestorcuentas.data.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "categoria")
public class CategoriaEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private UsuarioEntity usuario;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER )
    private Set<MovimientoEntity> movimientoEntitySet = new HashSet<>();

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "borrado")
    private Integer borrado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public Set<MovimientoEntity> getMovimientoEntitySet() {
        return movimientoEntitySet;
    }

    public void setMovimientoEntitySet(Set<MovimientoEntity> movimientoEntitySet) {
        this.movimientoEntitySet = movimientoEntitySet;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getBorrado() {
        return borrado;
    }

    public void setBorrado(Integer borrado) {
        this.borrado = borrado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriaEntity that = (CategoriaEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(usuario, that.usuario) && Objects.equals(movimientoEntitySet, that.movimientoEntitySet) && Objects.equals(nombre, that.nombre) && Objects.equals(borrado, that.borrado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuario, movimientoEntitySet, nombre, borrado);
    }

    @Override
    public String toString() {
        return "CategoriaEntity{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", movimientoEntitySet=" + movimientoEntitySet +
                ", nombre='" + nombre + '\'' +
                ", borrado=" + borrado +
                '}';
    }
}