package com.upc.g1tf.controllers;

import com.upc.g1tf.dtos.PacienteDTO;
import com.upc.g1tf.dtos.PacienteHistorialDTO;
import com.upc.g1tf.dtos.PacienteUpdateDTO;
import com.upc.g1tf.interfaces.IPacienteService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
@RequestMapping("/api")
public class PacienteController {
    @Autowired
    private IPacienteService pacienteService;

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESIONALSALUD')")
    @PostMapping("/nuevo_paciente")// HU02
    public ResponseEntity<PacienteDTO> registrarPaciente(@Valid @RequestBody PacienteDTO pacienteDTO) {
        PacienteDTO nuevoPaciente = pacienteService.registrarPaciente(pacienteDTO);
        return ResponseEntity.ok(nuevoPaciente);
    }
    @PutMapping("/actualizar_paciente/{id}") // HU10
    public ResponseEntity<PacienteDTO> actualizarPaciente(@PathVariable Integer id, @Valid @RequestBody PacienteUpdateDTO updateDTO) {
        PacienteDTO actualizado = pacienteService.actualizarPaciente(id, updateDTO);
        return ResponseEntity.ok(actualizado);
    }

    // ===== HU12 – Actualizar Antecedentes =====
    @PutMapping("/paciente/{id}/historial")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public ResponseEntity<PacienteDTO> actualizarHistorial(
            @PathVariable Integer id,
            @Valid @RequestBody PacienteHistorialDTO body) { // <-- este!
        return ResponseEntity.ok(pacienteService.actualizarHistorial(id, body));
    }

}