package com.tfg.gestorcuentas.data.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "criterio")
public class CriterioEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_categoria", referencedColumnName = "id")
    private CategoriaEntity categoria;

    @Column(name = "nombre", length = 48)
    private String nombre;

    @Column(name = "borrado")
    private Integer borrado;

    public CriterioEntity() {
    }

    public CriterioEntity(Integer id, CategoriaEntity categoria, String nombre, Integer borrado) {
        this.id = id;
        this.categoria = categoria;
        this.nombre = nombre;
        this.borrado = borrado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CategoriaEntity getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaEntity categoria) {
        this.categoria = categoria;
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
        CriterioEntity that = (CriterioEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(categoria, that.categoria) && Objects.equals(nombre, that.nombre) && Objects.equals(borrado, that.borrado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoria, nombre, borrado);
    }

    @Override
    public String toString() {
        return "CriterioEntity{" +
                "id=" + id +
                ", categoria=" + categoria +
                ", nombre='" + nombre + '\'' +
                ", borrado=" + borrado +
                '}';
    }
}
