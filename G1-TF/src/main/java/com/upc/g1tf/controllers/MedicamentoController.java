package com.upc.g1tf.controllers;

import com.upc.g1tf.dtos.MedicamentoDTO;
import com.upc.g1tf.interfaces.IMedicamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization")
@RequestMapping("/api")
public class MedicamentoController {

    @Autowired
    private IMedicamentoService medicamentoService;
    /**
     * HU14: Endpoint para registrar un nuevo medicamento.
     */
    @PostMapping("/medicamentos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MedicamentoDTO> registrarMedicamento(@Valid @RequestBody MedicamentoDTO medicamentoDTO) {
        MedicamentoDTO nuevoMedicamento = medicamentoService.insertarMedicamento(medicamentoDTO);
        // Se devuelve el objeto creado y el código de estado 201 CREATED
        return new ResponseEntity<>(nuevoMedicamento, HttpStatus.CREATED);
    }
    /**
     * HU15: Endpoint para listar todos los medicamentos del catálogo.
     * Accesible para roles ADMIN y DOCTOR.
     */
    @GetMapping("/medicamentos")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESIONALSALUD')")
    public ResponseEntity<List<MedicamentoDTO>> listarMedicamentos() {
        List<MedicamentoDTO> medicamentos = medicamentoService.listarMedicamentos();
        return new ResponseEntity<>(medicamentos, HttpStatus.OK);
    }
}