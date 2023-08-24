package com.tfg.gestorcuentas.service.login.model;

import java.util.Objects;

public class LoggedUser {
    private final String user;

    private final String token;

    private LoggedUser(Builder builder) {
        user = builder.user;
        token = builder.token;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String user;
        private String token;

        private Builder() {
        }

        public Builder withUser(String val) {
            user = val;
            return this;
        }

        public Builder withToken(String val) {
            token = val;
            return this;
        }

        public LoggedUser build() {
            return new LoggedUser(this);
        }
    }

    public String getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoggedUser that = (LoggedUser) o;
        return Objects.equals(user, that.user) && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, token);
    }
}
