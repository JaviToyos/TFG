package com.tfg.gestorcuentas.service.categoria.model;

public class CategoriaDTO {

    private Integer id;
    private String nombre;

    public CategoriaDTO() {
    }

    public CategoriaDTO(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
