package com.tfg.gestorcuentas.controller;

import com.tfg.gestorcuentas.service.criterio.ICriterioService;
import com.tfg.gestorcuentas.service.criterio.model.Criterio;
import com.tfg.gestorcuentas.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/criterio")
public class CriterioController {

    private final ICriterioService iCriterioService;

    @Autowired
    public CriterioController(ICriterioService iCriterioService) {
        this.iCriterioService = iCriterioService;
    }

    @ResponseBody
    @PostMapping(value = "/save")
    public ResponseEntity<String> save(@RequestBody Criterio criterio) {
        try {
            String criterioModificado = iCriterioService.save(criterio);
            if (criterioModificado.equals(Messages.CRITERIO_MODIFIED.getMessage()))
               return new ResponseEntity<>(Messages.CRITERIO_MODIFIED.getMessage(), HttpStatus.OK);

        } catch (final NoSuchElementException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (final IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
