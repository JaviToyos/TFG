package com.tfg.gestorcuentas.controller;

import com.tfg.gestorcuentas.service.movimiento.IMovimientoService;
import com.tfg.gestorcuentas.service.movimiento.model.Movimiento;
import com.tfg.gestorcuentas.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/movimientos")
public class MovimientoController {

    private final IMovimientoService iMovimientoService;

    @Autowired
    public MovimientoController(IMovimientoService iMovimientoService) {
        this.iMovimientoService = iMovimientoService;
    }

    @ResponseBody
    @PostMapping(value = "/save")
    public ResponseEntity<String> saveNordigenMovimientos(@RequestBody Movimiento movimiento) {
        try {
            String mensajeMovimientosGuardados = iMovimientoService.saveNordigen(movimiento);
            if (mensajeMovimientosGuardados.equals(Messages.MOVIMIENTOS_GUARDADO_OK.getMessage()))
                return new ResponseEntity<>(Messages.MOVIMIENTOS_GUARDADO_OK.getMessage(), HttpStatus.OK);

        } catch (final IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
