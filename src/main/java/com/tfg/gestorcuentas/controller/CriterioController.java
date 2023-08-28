package com.tfg.gestorcuentas.controller;

import com.tfg.gestorcuentas.data.entity.CriterioEntity;
import com.tfg.gestorcuentas.service.criterio.ICriterioService;
import com.tfg.gestorcuentas.service.criterio.model.Criterio;
import com.tfg.gestorcuentas.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    @PostMapping(value = "/addCriterio")
    public ResponseEntity<String> addCriterio(@RequestBody Criterio criterio) {
        try {
            String criterioModificado = iCriterioService.save(criterio);
            if (criterioModificado.equals(Messages.CRITERIO_SAVED.getMessage()))
               return new ResponseEntity<>(Messages.CRITERIO_SAVED.getMessage(), HttpStatus.OK);

        } catch (final NoSuchElementException | IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @PostMapping(value = "/modifyCriterio")
    public ResponseEntity<String> modifyCriterio(@RequestBody CriterioEntity criterio) {
        try {
            String criterioModificado = iCriterioService.modify(criterio);
            if (criterioModificado.equals(Messages.CRITERIO_MODIFIED.getMessage()))
                return new ResponseEntity<>(Messages.CRITERIO_MODIFIED.getMessage(), HttpStatus.OK);

        } catch (final IllegalStateException | IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @GetMapping(value = "/findByCategoria")
    public ResponseEntity<?> findCriterios(@RequestParam("categoria") Integer idCategoria) {
        try {
            List<CriterioEntity> criterioEntityList = iCriterioService.findByCategoria(idCategoria);
            if (criterioEntityList != null)
                return new ResponseEntity<>(criterioEntityList, HttpStatus.OK);
        } catch (final NoSuchElementException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (final IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @PostMapping(value = "/eliminar")
    public ResponseEntity<String> eliminarCriterio(@RequestBody Integer idCriterio) {
        try {
            String mensajeCriterioModificado = iCriterioService.delete(idCriterio);
            if (mensajeCriterioModificado.equals(Messages.CRITERIO_MODIFIED.getMessage()))
                return new ResponseEntity<>(Messages.CRITERIO_MODIFIED.getMessage(), HttpStatus.OK);

        } catch (final IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
