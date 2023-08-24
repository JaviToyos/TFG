package com.tfg.gestorcuentas.service.login.model;

public class Login {
    private final String username;
    private final String passwd;

    public Login(String username, String passwd) {
        this.username = username;
        this.passwd = passwd;
    }

    public static Builder builder() {
        return new Builder();
    }

    private Login(Builder builder) {
        username = builder.userName;
        passwd = builder.passwd;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswd() {
        return passwd;
    }

    public static final class Builder {
        private String userName;
        private String passwd;

        private Builder() {
        }

        public Builder withUserName(String val) {
            userName = val;
            return this;
        }

        public Builder withPasswd(String val) {
            passwd = val;
            return this;
        }

        public Login build() {
            return new Login(this);
        }
    }
}
