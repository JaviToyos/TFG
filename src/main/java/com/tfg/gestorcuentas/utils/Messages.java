package com.tfg.gestorcuentas.utils;

public enum Messages {

    GUARDADO_OK("El usuario y su información de persona han sido guardados correctamente."),
    PASSWORD_MODIFIED("La contraseña del usuario se ha modificado orrectamente."),
    MAIL_ENVIADO_OK("El email se ha enviado correctamente."),
    PERSONAL_DATA_MODIFIED("Los datos personales se han modificado correctamente."),
    CRITERIO_MODIFIED("El criterio se ha modificado correctamente."),
    CRITERIO_SAVED("El criterio se ha guardado correctamente."),
    CUENTA_BANCARIA_MODIFIED("La cuenta bancaria se ha modificado correctamente."),
    CATEGORIA_MODIFIED("La categoria se ha modificado correctamente."),
    CUENTA_BANCARIA_DELETED("La cuenta bancaria se ha eliminado correctamente."),
    CUENTA_BANCARIA_GUARDADA("La cuenta bancaria se ha guardado correctamente"),
    CATEGORIA_GUARDADA("Categoria guardada correctamente."),
    MOVIMIENTOS_GUARDADO_OK("Los movimientos se han guardado correctamente"),
    MOVIMIENTOS_MODIFICADO_OK("Los movimientos se han modificado correctamente");

    private String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
