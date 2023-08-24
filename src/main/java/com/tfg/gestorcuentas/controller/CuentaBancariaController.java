package com.tfg.gestorcuentas.controller;

import com.tfg.gestorcuentas.data.entity.CuentaBancariaEntity;
import com.tfg.gestorcuentas.service.cuentaBancaria.ICuentaBancariaService;
import com.tfg.gestorcuentas.service.cuentaBancaria.model.CuentaBancaria;
import com.tfg.gestorcuentas.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

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

    @ResponseBody
    @GetMapping(value = "/findByUsername")
    public ResponseEntity<?> findCuentasBancarias(@RequestParam("username") String username) {
        try {
            List<CuentaBancariaEntity> cuentaBancariaEntityList = iCuentaBancariaService.findByUsername(username);
            if (cuentaBancariaEntityList != null)
                return new ResponseEntity<>(cuentaBancariaEntityList, HttpStatus.OK);
        } catch (final NoSuchElementException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (final IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @PostMapping(value = "/deleteCuenta")
    public ResponseEntity<String> deleteNordigen(@RequestBody CuentaBancaria cuentaBancaria) {
        try {
            String mensajeCuentaGuardada = iCuentaBancariaService.deleteCuenta(cuentaBancaria);
            if (mensajeCuentaGuardada.equals(Messages.CUENTA_BANCARIA_DELETED.getMessage()))
                return new ResponseEntity<>(Messages.CUENTA_BANCARIA_DELETED.getMessage(), HttpStatus.OK);

        } catch (final IllegalArgumentException | NoSuchElementException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
