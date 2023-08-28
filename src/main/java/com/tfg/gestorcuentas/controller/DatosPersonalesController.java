package com.tfg.gestorcuentas.controller;

import com.tfg.gestorcuentas.service.datosPersonales.IDatosPersonalesService;
import com.tfg.gestorcuentas.service.datosPersonales.model.DatosPersonales;
import com.tfg.gestorcuentas.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/datosPersonales")
public class DatosPersonalesController {
    private final IDatosPersonalesService iDatosPersonalesService;

    @Autowired
    public DatosPersonalesController(IDatosPersonalesService iDatosPersonalesService) {
        this.iDatosPersonalesService = iDatosPersonalesService;
    }

    @ResponseBody
    @PostMapping(value = "/update")
    public ResponseEntity<String> updateDatosPersonales(@RequestBody DatosPersonales datosPersonales) {
        try {
            String datosModificados = iDatosPersonalesService.updateDatosPersonales(datosPersonales);
            if (datosModificados.equals(Messages.PERSONAL_DATA_MODIFIED.getMessage()))
                return new ResponseEntity<>(Messages.PERSONAL_DATA_MODIFIED.getMessage(), HttpStatus.OK);

        } catch (final NoSuchElementException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (final IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @GetMapping(value = "/findByUsername")
    public ResponseEntity<?> findDatosPersonales(@RequestParam("username") String username) {
        try {
            DatosPersonales findUser = iDatosPersonalesService.findByUsername(username);
            if (findUser != null)
                return new ResponseEntity<>(findUser, HttpStatus.OK);
        } catch (final NoSuchElementException | IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
