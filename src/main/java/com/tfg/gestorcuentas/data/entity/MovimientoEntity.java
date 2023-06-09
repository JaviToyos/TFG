package com.tfg.gestorcuentas.data.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "movimiento")
public class MovimientoEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_cuenta_bancaria", referencedColumnName = "id")
    private CuentaBancariaEntity cuentaBancaria;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "categoria_movimiento", joinColumns = {@JoinColumn(name = "id_movimiento")}, inverseJoinColumns = {
            @JoinColumn(name = "id_categoria")})
    private Set<CategoriaEntity> categoriaEntitySet = new HashSet<>();

    @Column(name = "id_transaccion")
    private String idTransaccion;

    @Column(name = "cantidad")
    private Double cantidad;

    @Column(name = "divisa")
    private String divisa;

    @Column(name = "destinatario")
    private String destinatario;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "borrado")
    private Integer borrado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CuentaBancariaEntity getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setCuentaBancaria(CuentaBancariaEntity cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

    public Set<CategoriaEntity> getCategoriaEntitySet() {
        return categoriaEntitySet;
    }

    public void setCategoriaEntitySet(Set<CategoriaEntity> categoriaEntitySet) {
        this.categoriaEntitySet = categoriaEntitySet;
    }

    public String getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public String getDivisa() {
        return divisa;
    }

    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
        MovimientoEntity that = (MovimientoEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(cuentaBancaria, that.cuentaBancaria) && Objects.equals(categoriaEntitySet, that.categoriaEntitySet) && Objects.equals(idTransaccion, that.idTransaccion) && Objects.equals(cantidad, that.cantidad) && Objects.equals(divisa, that.divisa) && Objects.equals(destinatario, that.destinatario) && Objects.equals(fecha, that.fecha) && Objects.equals(borrado, that.borrado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cuentaBancaria, categoriaEntitySet, idTransaccion, cantidad, divisa, destinatario, fecha, borrado);
    }

    @Override
    public String toString() {
        return "MovimientoEntity{" +
                "id=" + id +
                ", cuentaBancaria=" + cuentaBancaria +
                ", categoriaEntitySet=" + categoriaEntitySet +
                ", idTransaccion='" + idTransaccion + '\'' +
                ", cantidad=" + cantidad +
                ", divisa='" + divisa + '\'' +
                ", destinatario='" + destinatario + '\'' +
                ", fecha=" + fecha +
                ", borrado=" + borrado +
                '}';
    }
}
