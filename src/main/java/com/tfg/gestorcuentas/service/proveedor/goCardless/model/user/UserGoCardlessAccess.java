package com.tfg.gestorcuentas.service.proveedor.goCardless.model.user;

public class UserGoCardlessAccess {
    private String access;
    private Integer access_expires;
    private String refresh;
    private Integer refresh_expires;

    public UserGoCardlessAccess() {
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public Integer getAccess_expires() {
        return access_expires;
    }

    public void setAccess_expires(Integer access_expires) {
        this.access_expires = access_expires;
    }

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }

    public Integer getRefresh_expires() {
        return refresh_expires;
    }

    public void setRefresh_expires(Integer refresh_expires) {
        this.refresh_expires = refresh_expires;
    }
}
