package com.tfg.gestorcuentas.controller;

import com.tfg.gestorcuentas.service.cuentaBancaria.ICuentaBancariaService;
import com.tfg.gestorcuentas.service.cuentaBancaria.model.CuentaBancaria;
import com.tfg.gestorcuentas.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/cuentaBancaria")
public class CuentaBancariaController {
    private ICuentaBancariaService iCuentaBancariaService;

    @Autowired
    public CuentaBancariaController(ICuentaBancariaService iCuentaBancariaService) {
        this.iCuentaBancariaService = iCuentaBancariaService;
    }

    @ResponseBody
    @PostMapping(value = "/save")
    public ResponseEntity<String> saveNordigen(@RequestBody CuentaBancaria cuentaBancaria) {
        try {
            String mensajeCuentaGuardada = iCuentaBancariaService.saveNordigen(cuentaBancaria);
            if (mensajeCuentaGuardada.equals(Messages.CUENTA_BANCARIA_GUARDADA.getMessage()))
                return new ResponseEntity<>(Messages.CUENTA_BANCARIA_GUARDADA.getMessage(), HttpStatus.OK);

        } catch (final IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
