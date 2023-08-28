package com.tfg.gestorcuentas.data.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "cuenta_bancaria")
public class CuentaBancariaEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private UsuarioEntity usuario;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id")
    private ProveedorEntity proveedor;

    @Column(name = "id_cuenta")
    private String accountID;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "iban", length = 48)
    private String iban;

    @Column(name = "moneda", length = 16)
    private String moneda;

    @Column(name = "cantidad")
    private Double cantidad;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "borrado")
    private Integer borrado;

    public CuentaBancariaEntity() {
    }

    public CuentaBancariaEntity(Integer id, UsuarioEntity usuario, ProveedorEntity proveedor, String accountID, String nombre, String iban, String moneda, Double cantidad, Date fecha, Integer borrado) {
        this.id = id;
        this.usuario = usuario;
        this.proveedor = proveedor;
        this.accountID = accountID;
        this.nombre = nombre;
        this.iban = iban;
        this.moneda = moneda;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.borrado = borrado;
    }

    public Integer getId() {
        return id;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public ProveedorEntity getProveedor() {
        return proveedor;
    }

    public String getAccountID() {
        return accountID;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIban() {
        return iban;
    }

    public String getMoneda() {
        return moneda;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public Integer getBorrado() {
        return borrado;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public void setProveedor(ProveedorEntity proveedor) {
        this.proveedor = proveedor;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setBorrado(Integer borrado) {
        this.borrado = borrado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CuentaBancariaEntity that = (CuentaBancariaEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(usuario, that.usuario) && Objects.equals(proveedor, that.proveedor) && Objects.equals(accountID, that.accountID) && Objects.equals(nombre, that.nombre) && Objects.equals(iban, that.iban) && Objects.equals(moneda, that.moneda) && Objects.equals(cantidad, that.cantidad) && Objects.equals(fecha, that.fecha) && Objects.equals(borrado, that.borrado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuario, proveedor, accountID, nombre, iban, moneda, cantidad, fecha, borrado);
    }

    @Override
    public String toString() {
        return "CuentaBancariaEntity{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", proveedor=" + proveedor +
                ", accountID='" + accountID + '\'' +
                ", nombre='" + nombre + '\'' +
                ", iban='" + iban + '\'' +
                ", moneda='" + moneda + '\'' +
                ", cantidad=" + cantidad +
                ", fecha=" + fecha +
                ", borrado=" + borrado +
                '}';
    }
}
