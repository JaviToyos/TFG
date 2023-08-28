package com.tfg.gestorcuentas.data.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "movimiento")
public class MovimientoEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_cuenta_bancaria", referencedColumnName = "id")
    private CuentaBancariaEntity cuentaBancaria;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "categoria_movimiento", joinColumns = {@JoinColumn(name = "id_movimiento")},
            inverseJoinColumns = {@JoinColumn(name = "id_categoria")})
    private Set<CategoriaEntity> categoriaEntitySet = new HashSet<>();

    @Column(name="informacion_movimiento")
    private String informacionMovimiento;

    @Column(name = "id_transaccion")
    private String idTransaccion;

    @Column(name = "cantidad", length = 16)
    private String cantidad;

    @Column(name = "divisa", length = 16)
    private String divisa;

    @Column(name = "destinatario", length = 48)
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

    public MovimientoEntity() {
    }

    public MovimientoEntity(Integer id, CuentaBancariaEntity cuentaBancaria, Set<CategoriaEntity> categoriaEntitySet, String informacionMovimiento, String idTransaccion, String cantidad, String divisa, String destinatario, Date fecha, Integer borrado) {
        this.id = id;
        this.cuentaBancaria = cuentaBancaria;
        this.categoriaEntitySet = categoriaEntitySet;
        this.informacionMovimiento = informacionMovimiento;
        this.idTransaccion = idTransaccion;
        this.cantidad = cantidad;
        this.divisa = divisa;
        this.destinatario = destinatario;
        this.fecha = fecha;
        this.borrado = borrado;
    }

    public String getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
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

    public String getInformacionMovimiento() {
        return informacionMovimiento;
    }

    public void setInformacionMovimiento(String informacionMovimiento) {
        this.informacionMovimiento = informacionMovimiento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovimientoEntity that = (MovimientoEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(cuentaBancaria, that.cuentaBancaria) && Objects.equals(categoriaEntitySet, that.categoriaEntitySet) && Objects.equals(informacionMovimiento, that.informacionMovimiento) && Objects.equals(idTransaccion, that.idTransaccion) && Objects.equals(cantidad, that.cantidad) && Objects.equals(divisa, that.divisa) && Objects.equals(destinatario, that.destinatario) && Objects.equals(fecha, that.fecha) && Objects.equals(borrado, that.borrado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cuentaBancaria, categoriaEntitySet, informacionMovimiento, idTransaccion, cantidad, divisa, destinatario, fecha, borrado);
    }

    @Override
    public String toString() {
        return "MovimientoEntity{" +
                "id=" + id +
                ", cuentaBancaria=" + cuentaBancaria +
                ", categoriaEntitySet=" + categoriaEntitySet +
                ", informacionMovimiento='" + informacionMovimiento + '\'' +
                ", idTransaccion='" + idTransaccion + '\'' +
                ", cantidad=" + cantidad +
                ", divisa='" + divisa + '\'' +
                ", destinatario='" + destinatario + '\'' +
                ", fecha=" + fecha +
                ", borrado=" + borrado +
                '}';
    }
}
