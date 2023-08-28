package com.tfg.gestorcuentas.data.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "usuario")
public class UsuarioEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private PersonaEntity persona;

    @Column(name = "username", length = 48)
    private String username;

    @Column(name = "password", length = 48)
    private String password;

    @Column(name = "borrado")
    private Integer borrado;

    public UsuarioEntity() {

    }

    public UsuarioEntity(Integer id, String username, String password, Integer borrado) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.borrado = borrado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PersonaEntity getPersona() {
        return persona;
    }

    public void setPersona(PersonaEntity persona) {
        this.persona = persona;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        UsuarioEntity that = (UsuarioEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(persona, that.persona) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(borrado, that.borrado);
    }

    @Override
    public String toString() {
        return "UsuarioEntity{" +
                "id=" + id +
                ", persona=" + persona +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", borrado=" + borrado +
                '}';
    }
}