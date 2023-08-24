package com.tfg.gestorcuentas.data.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "persona")
public class PersonaEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private UsuarioEntity usuario;

    @Column(name = "dni")
    private String dni;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "email")
    private String email;

    @Column(name = "borrado")
    private Integer borrado;

    public Integer getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getEmail() {
        return email;
    }

    public Integer getBorrado() {
        return borrado;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBorrado(Integer borrado) {
        this.borrado = borrado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonaEntity persona = (PersonaEntity) o;
        return Objects.equals(id, persona.id) && Objects.equals(usuario, persona.usuario) && Objects.equals(dni, persona.dni) && Objects.equals(nombre, persona.nombre) && Objects.equals(apellidos, persona.apellidos) && Objects.equals(email, persona.email) && Objects.equals(borrado, persona.borrado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuario, dni, nombre, apellidos, email, borrado);
    }

    @Override
    public String toString() {
        return "PersonaEntity{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", borrado=" + borrado +
                '}';
    }
}
