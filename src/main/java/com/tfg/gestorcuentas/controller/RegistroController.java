package com.tfg.gestorcuentas.controller;

import com.tfg.gestorcuentas.service.registro.IRegistroService;
import com.tfg.gestorcuentas.service.registro.model.Registro;
import com.tfg.gestorcuentas.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/registro")
public class RegistroController {
    private IRegistroService iRegistroService;

    @Autowired
    public RegistroController(IRegistroService iRegistroService) {
        this.iRegistroService = iRegistroService;
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity<String> registro(@RequestBody Registro registro) {
        try {
            String mensajeUsuarioRegistrado = iRegistroService.registro(registro);
            if (mensajeUsuarioRegistrado.equals(Messages.GUARDADO_OK.getMessage()))
                return new ResponseEntity<>(Messages.GUARDADO_OK.getMessage(), HttpStatus.OK);

        } catch (final IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
