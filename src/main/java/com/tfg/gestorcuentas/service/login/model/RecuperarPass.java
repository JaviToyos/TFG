package com.tfg.gestorcuentas.service.login.model;

public class RecuperarPass {

    private final String username;
    private final String email;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public RecuperarPass(String username, String email) {
        this.username = username;
        this.email = email;
    }

    private RecuperarPass(Builder builder) {
        username = builder.username;
        email = builder.email;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String username;
        private String email;

        private Builder() {
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public RecuperarPass build() {
            return new RecuperarPass(this);
        }
    }
}
