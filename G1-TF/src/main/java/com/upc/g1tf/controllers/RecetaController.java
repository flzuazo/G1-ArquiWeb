package com.upc.g1tf.controllers;

import com.upc.g1tf.dtos.RecetaDTO;
import com.upc.g1tf.interfaces.IRecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
@RequestMapping("/api")
public class RecetaController {

    @Autowired
    private IRecetaService recetaService;

    // ---------- Insertar receta ----------
    @PostMapping("/nueva_receta")
    public ResponseEntity<RecetaDTO> insertarReceta(@RequestBody RecetaDTO recetaDTO) {
        RecetaDTO nuevaReceta = recetaService.insertarReceta(recetaDTO);
        return ResponseEntity.ok(nuevaReceta);
    }

    // ---------- Listar todas las recetas ----------
    @GetMapping("/recetas")
    public ResponseEntity<List<RecetaDTO>> listarRecetas() {
        List<RecetaDTO> recetas = recetaService.listarRecetas();
        return ResponseEntity.ok(recetas);
    }

    // ---------- Buscar receta por ID ----------
    @GetMapping("/buscar_receta/{id}")
    public ResponseEntity<RecetaDTO> buscarRecetaPorId(@PathVariable Integer id) {
        RecetaDTO receta = recetaService.buscarRecetaPorId(id);
        return ResponseEntity.ok(receta);
    }

    // ---------- Modificar receta ----------
    @PutMapping("/modificar_receta")
    public ResponseEntity<RecetaDTO> modificarReceta(@RequestBody RecetaDTO recetaDTO) {
        RecetaDTO recetaModificada = recetaService.modificarReceta(recetaDTO);
        return ResponseEntity.ok(recetaModificada);
    }

    // ---------- Eliminar receta ----------
    @DeleteMapping("/eliminar_receta/{id}")
    public ResponseEntity<Void> eliminarReceta(@PathVariable Integer id) {
        recetaService.eliminarReceta(id);
        return ResponseEntity.noContent().build();
    }
}