package com.tfg.gestorcuentas.controller;

import com.tfg.gestorcuentas.service.categoria.ICategoriaService;
import com.tfg.gestorcuentas.service.categoria.model.Categoria;
import com.tfg.gestorcuentas.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/categoria")
public class CategoriaController {
    private final ICategoriaService iCategoriaService;

    @Autowired
    public CategoriaController(ICategoriaService iCategoriaService) {
        this.iCategoriaService = iCategoriaService;
    }

    @ResponseBody
    @PostMapping(value = "/save")
    public ResponseEntity<String> save(@RequestBody Categoria categoria) {
        try {
            String mensajeCategoriaGuardada = iCategoriaService.save(categoria);
            if (mensajeCategoriaGuardada.equals(Messages.CATEGORIA_GUARDADA.getMessage()))
                return new ResponseEntity<>(Messages.CATEGORIA_GUARDADA.getMessage(), HttpStatus.OK);

        } catch (final IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @PostMapping(value = "/modificar")
    public ResponseEntity<String> modify(@RequestBody Categoria categoria) {
        try {
            String mensajeCategoriaModificada = iCategoriaService.modify(categoria);
            if (mensajeCategoriaModificada.equals(Messages.CATEGORIA_MODIFIED.getMessage()))
                return new ResponseEntity<>(Messages.CATEGORIA_MODIFIED.getMessage(), HttpStatus.OK);

        } catch (final IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
