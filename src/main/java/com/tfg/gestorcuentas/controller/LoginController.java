package com.tfg.gestorcuentas.controller;

import com.tfg.gestorcuentas.service.login.ILoginService;
import com.tfg.gestorcuentas.service.login.model.LoggedUser;
import com.tfg.gestorcuentas.service.login.model.Login;
import com.tfg.gestorcuentas.service.login.model.RecuperarPass;
import com.tfg.gestorcuentas.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

    private final ILoginService iLoginService;

    @Autowired
    public LoginController(ILoginService iLoginService) {
        this.iLoginService = iLoginService;
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity<?> login(@RequestBody Login login) {
        try {
            LoggedUser loggedUser = iLoginService.login(login);
            if (loggedUser.getToken() != null) return new ResponseEntity<>(loggedUser, HttpStatus.OK);

        } catch (final IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @PostMapping(value = "/passBackUp")
    public ResponseEntity<?> passBackup(@RequestBody RecuperarPass recuperarPass) {
        try {

            String str = iLoginService.recuperarPasswdUsuario(recuperarPass);
            if (str.equals(Messages.PASSWORD_MODIFIED.getMessage()))
                return new ResponseEntity<>(Messages.MAIL_ENVIADO_OK.getMessage(), HttpStatus.OK);

        } catch (IllegalArgumentException | IllegalStateException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
