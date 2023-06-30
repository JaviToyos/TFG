package com.tfg.gestorcuentas.mail.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Mail {

    private final String emisorEmail;
    private final String receptorEmail;
    private final String asuntoEmail;
    private final String contenidoEmail;
    private final String tipoContenido;
    private final List<Object> adjuntos;

    private Mail(Builder builder) {
        emisorEmail = builder.emisorEmail;
        receptorEmail = builder.receptorEmail;
        asuntoEmail = builder.asuntoEmail;
        contenidoEmail = builder.contenidoEmail;
        tipoContenido = builder.tipoContenido;
        adjuntos = builder.adjuntos;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getEmisorEmail() {

        return emisorEmail;
    }

    public String getReceptorEmail() {
        return receptorEmail;
    }

    public String getAsuntoEmail() {
        return asuntoEmail;
    }

    public String getContenidoEmail() {
        return contenidoEmail;
    }

    public String getTipoContenido() {
        return tipoContenido;
    }

    public List<Object> getAdjuntos() {
        return adjuntos;
    }

    public static class Builder {
        private String emisorEmail;
        private String receptorEmail;
        private String asuntoEmail;
        private String contenidoEmail;
        private String tipoContenido;
        private List<Object> adjuntos = Collections.emptyList();

        private Builder() {
        }

        public Builder withEmisorEmail(String emisorEmail) {
            this.emisorEmail = emisorEmail;
            return this;
        }

        public Builder withReceptorEmail(String receptorEmail) {
            this.receptorEmail = receptorEmail;
            return this;
        }

        public Builder withAsuntoEmail(String asuntoEmail) {
            this.asuntoEmail = asuntoEmail;
            return this;
        }

        public Builder withContenidoEmail(String contenidoEmail) {
            this.contenidoEmail = contenidoEmail;
            return this;
        }

        public Builder withTipoContenido() {
            this.tipoContenido = "text/plain";
            return this;
        }

        public Builder withAdjuntos(List<Object> adjuntos) {
            this.adjuntos = adjuntos;
            return this;
        }

        public Mail build() {
            return new Mail(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mail mail = (Mail) o;
        return Objects.equals(emisorEmail, mail.emisorEmail) && Objects.equals(receptorEmail, mail.receptorEmail) && Objects.equals(asuntoEmail, mail.asuntoEmail) && Objects.equals(contenidoEmail, mail.contenidoEmail) && Objects.equals(tipoContenido, mail.tipoContenido) && Objects.equals(adjuntos, mail.adjuntos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emisorEmail, receptorEmail, asuntoEmail, contenidoEmail, tipoContenido, adjuntos);
    }

    @Override
    public String toString() {
        return "Mail{" +
                "emisorEmail='" + emisorEmail + '\'' +
                ", receptorEmail='" + receptorEmail + '\'' +
                ", asuntoEmail='" + asuntoEmail + '\'' +
                ", contenidoEmail='" + contenidoEmail + '\'' +
                ", tipoContenido='" + tipoContenido + '\'' +
                ", adjuntos=" + adjuntos +
                '}';
    }
}
