package com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.userAccounts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

public class StringStatus implements Serializable {
    @JsonIgnoreProperties
    private String sho;
    @JsonIgnoreProperties
    private String lon;
    private String description;

    public StringStatus() {
    }

    public StringStatus(String sho, String lon, String description) {
        this.sho = sho;
        this.lon = lon;
        this.description = description;
    }

    public String getSho() {
        return sho;
    }

    public String getLon() {
        return lon;
    }

    public String getDescription() {
        return description;
    }
}
