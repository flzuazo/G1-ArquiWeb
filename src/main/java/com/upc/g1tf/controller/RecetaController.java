package com.upc.g1tf.controller;

import com.upc.g1tf.dtos.RecetaDTO;
import com.upc.g1tf.interfaces.IRecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recetas")
public class RecetaController {

    @Autowired
    private IRecetaService recetaService;

    // ---------- Insertar receta ----------
    @PostMapping
    public ResponseEntity<RecetaDTO> insertarReceta(@RequestBody RecetaDTO recetaDTO) {
        RecetaDTO nuevaReceta = recetaService.insertarReceta(recetaDTO);
        return ResponseEntity.ok(nuevaReceta);
    }

    // ---------- Listar todas las recetas ----------
    @GetMapping
    public ResponseEntity<List<RecetaDTO>> listarRecetas() {
        List<RecetaDTO> recetas = recetaService.listarRecetas();
        return ResponseEntity.ok(recetas);
    }

    // ---------- Buscar receta por ID ----------
    @GetMapping("/{id}")
    public ResponseEntity<RecetaDTO> buscarRecetaPorId(@PathVariable Long id) {
        RecetaDTO receta = recetaService.buscarRecetaPorId(id);
        return ResponseEntity.ok(receta);
    }

    // ---------- Modificar receta ----------
    @PutMapping
    public ResponseEntity<RecetaDTO> modificarReceta(@RequestBody RecetaDTO recetaDTO) {
        RecetaDTO recetaModificada = recetaService.modificarReceta(recetaDTO);
        return ResponseEntity.ok(recetaModificada);
    }

    // ---------- Eliminar receta ----------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReceta(@PathVariable Long id) {
        recetaService.eliminarReceta(id);
        return ResponseEntity.noContent().build();
    }
}
