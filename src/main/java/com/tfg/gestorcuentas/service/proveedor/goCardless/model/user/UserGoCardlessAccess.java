package com.tfg.gestorcuentas.service.proveedor.goCardless.model.user;

import java.util.Objects;

public class UserGoCardlessAccess {
    private String access;
    private Integer access_expires;
    private String refresh;
    private Integer refresh_expires;

    public UserGoCardlessAccess() {
    }

    public UserGoCardlessAccess(String access, Integer access_expires, String refresh, Integer refresh_expires) {
        this.access = access;
        this.access_expires = access_expires;
        this.refresh = refresh;
        this.refresh_expires = refresh_expires;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGoCardlessAccess that = (UserGoCardlessAccess) o;
        return Objects.equals(access, that.access) && Objects.equals(access_expires, that.access_expires) && Objects.equals(refresh, that.refresh) && Objects.equals(refresh_expires, that.refresh_expires);
    }

    @Override
    public int hashCode() {
        return Objects.hash(access, access_expires, refresh, refresh_expires);
    }
}
