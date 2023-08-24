package com.tfg.gestorcuentas.utils;

public enum MessageErrors {
    JWT_EXPIRED("El token de sesión ha expirado."),
    JWT_NOT_ALLOWED("El token de sesión no es válido."),
    JWT_MALFORMED("El token de sesión está mal formado."),

    ANY_PROPERTY_NULL("Uno de los atributos del objeto es nulo."),

    LOGIN_EMPTY("El usuario o la contraseña están vacios."),
    PASS_BACKUP_EMPTY("El usuario o el email llegan vacios al recuperar la contraseña."),
    PASS_BACKUP_NOT_VALID("El usuario o el email no se corresponden con los pasados por parámetro."),
    USER_NOT_FOUND("El usuario no ha sido encontrado."),
    USER_TOKEN_NULL("El token de usuario no puede ser null. "),
    BANK_SELECTED_NULL("El banco seleccionado no puede ser null"),
    BANKLINK_NULL("El bankLink no puede ser null"),
    ACCOUNT_EMPTY_OR_NULL("La cuenta bancaria no puede llegar vacia."),
    ACCOUNT_DATA_EMPTY("Los datos de la cuenta bancaria no pueden llegar vacios."),
    ACCOUNT_DETAILS_EMPTY("Los detalles de la cuenta bancaria no pueden llegar vacios."),
    ACCOUNT_BALANCE_EMPTY("El balance de la cuenta bancaria no puede llegar vacio."),
    MOVIMIENTO_DATA_EMPTY("Los datos del movimiento no pueden estar vacios."),
    USER_DATA_EMPTY("Los datos de usuario no pueden estar vacios"),
    CATEGORIA_EMPTY("Los datos de la categoria no pueden ser vacios"),
    BANK_ACCOUNT_NOT_FOUND("La cuenta bancaria no ha sido encontrada"),
    BANK_ACCOUNT_ALREADY_EXISTS("La cuenta bancaria que desea añadir ya existe"),

    DNI_FOUND("El dni proporcionado ya existe en la base de datos."),
    USERNAME_FOUND("El nombre de usuario proporcionado ya existe en la base de datos."),
    GUARDADO_NOT_OK("No se ha podido guardar correctamente el usuario en la base de datos."),
    MAIL_NOT_SENT("El mail no se ha podido enviar. "),
    PERSON_NOT_FOUND("La persona no ha sido encontrada."),
    PESONAL_DATA_EMPTY("Alguno de los datos personales viene vacío."),

    PROVIDER_NAME_NULL("El nombre del proveedor es nulo."),
    PROVIDER_NOT_FOUND("El proveedor no ha sido encontrado."),
    USER_TOKEN_NOT_FOUND("No se ha podido encontrar el secretID y secretKey del usuario."),
    PARAM_NOT_FOUND("No se ha podido encontrar el parámetro de las credenciales del proveedor"),
    PROVIDER_CREDENTIALS_NOT_FOUND("No se han podido encontrar las credenciales del proveedor."),

    CATEGORIA_NOT_FOUND("No se ha podido encontrar la categoría"),
    ID_CATEGORIA_NULL("El id de la categoria es null"),

    CRITERIO_NOT_FOUND("No se ha podido encontrar el criterio"),
    CRITERIO_ALREADY_EXISTS("El criterio que desea introducir ya existe"),
    ID_CRITERIO_NULL("El id del criterio es null");

    private String errorCode;

    MessageErrors(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
