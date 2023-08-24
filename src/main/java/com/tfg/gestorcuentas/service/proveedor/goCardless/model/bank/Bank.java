package com.tfg.gestorcuentas.service.proveedor.goCardless.model.bank;

import java.io.Serializable;
import java.util.List;

public class Bank implements Serializable {
    private String id;
    private String name;
    private String bic;
    private Integer transaction_total_days;
    private List<String> countries;
    private String logo;

    public Bank() {
    }

    public Bank(String id, String name, String bic, Integer transaction_total_days, List<String> countries, String logo) {
        this.id = id;
        this.name = name;
        this.bic = bic;
        this.transaction_total_days = transaction_total_days;
        this.countries = countries;
        this.logo = logo;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBic() {
        return bic;
    }

    public Integer getTransaction_total_days() {
        return transaction_total_days;
    }

    public List<String> getCountries() {
        return countries;
    }

    public String getLogo() {
        return logo;
    }
}
