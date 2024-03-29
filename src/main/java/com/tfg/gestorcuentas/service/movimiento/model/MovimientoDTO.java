package com.tfg.gestorcuentas.service.movimiento.model;

import com.tfg.gestorcuentas.service.categoria.model.CategoriaDTO;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MovimientoDTO {

    private Integer id;
    private String destinatario;
    private String informacionMovimiento;
    private String idTransaccion;
    private String cantidad;
    private String divisa;
    private Date fecha;
    private List<CategoriaDTO> categorias;

    public MovimientoDTO() {
    }

    public MovimientoDTO(Integer id, String destinatario, String informacionMovimiento, String idTransaccion, String cantidad, String divisa, Date fecha, List<CategoriaDTO> categorias) {
        this.id = id;
        this.destinatario = destinatario;
        this.informacionMovimiento = informacionMovimiento;
        this.idTransaccion = idTransaccion;
        this.cantidad = cantidad;
        this.divisa = divisa;
        this.fecha = fecha;
        this.categorias = categorias;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getInformacionMovimiento() {
        return informacionMovimiento;
    }

    public void setInformacionMovimiento(String informacionMovimiento) {
        this.informacionMovimiento = informacionMovimiento;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<CategoriaDTO> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaDTO> categorias) {
        this.categorias = categorias;
    }

    public String getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovimientoDTO that = (MovimientoDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(destinatario, that.destinatario) && Objects.equals(informacionMovimiento, that.informacionMovimiento) && Objects.equals(idTransaccion, that.idTransaccion) && Objects.equals(cantidad, that.cantidad) && Objects.equals(divisa, that.divisa) && Objects.equals(fecha, that.fecha) && Objects.equals(categorias, that.categorias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, destinatario, informacionMovimiento, idTransaccion, cantidad, divisa, fecha, categorias);
    }

    @Override
    public String toString() {
        return "MovimientoDTO{" +
                "id=" + id +
                ", destinatario='" + destinatario + '\'' +
                ", informacionMovimiento='" + informacionMovimiento + '\'' +
                ", idTransaccion='" + idTransaccion + '\'' +
                ", cantidad='" + cantidad + '\'' +
                ", divisa='" + divisa + '\'' +
                ", fecha=" + fecha +
                ", categorias=" + categorias +
                '}';
    }
}
