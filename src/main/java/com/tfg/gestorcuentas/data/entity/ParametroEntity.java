package com.tfg.gestorcuentas.data.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "parametro")
public class ParametroEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id")
    private ProveedorEntity proveedor;

    @Column(name = "atributo")
    private String atributo;

    @Column(name = "valor")
    private String valor;

    public Integer getId() {
        return id;    }


    public ParametroEntity() {
    }

    public ParametroEntity(Integer id, ProveedorEntity proveedor, String atributo, String valor) {
        this.id = id;
        this.proveedor = proveedor;
        this.atributo = atributo;
        this.valor = valor;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProveedorEntity getProveedor() {
        return proveedor;
    }

    public void setCredencialesProveedor(ProveedorEntity proveedorEntity) {
        this.proveedor = proveedorEntity;
    }

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParametroEntity that = (ParametroEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(proveedor, that.proveedor) && Objects.equals(atributo, that.atributo) && Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, proveedor, atributo, valor);
    }

    @Override
    public String toString() {
        return "ParametroEntity{" +
                "id=" + id +
                ", proveedor=" + proveedor +
                ", atributo='" + atributo + '\'' +
                ", valor='" + valor + '\'' +
                '}';
    }
}