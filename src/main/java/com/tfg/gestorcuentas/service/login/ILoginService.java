package com.tfg.gestorcuentas.service.login;

import com.tfg.gestorcuentas.service.login.model.LoggedUser;
import com.tfg.gestorcuentas.service.login.model.Login;
import com.tfg.gestorcuentas.service.login.model.RecuperarPass;

public interface ILoginService {

    LoggedUser login(final Login login);

    String recuperarPasswdUsuario(RecuperarPass recuperarPass);
}
