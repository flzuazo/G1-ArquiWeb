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

import java.util.List;


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
    @PutMapping("/paciente/historial/{id}")
    //@PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public ResponseEntity<PacienteDTO> actualizarHistorial(
            @PathVariable Integer id,
            @Valid @RequestBody PacienteHistorialDTO body) { // <-- este
        return ResponseEntity.ok(pacienteService.actualizarHistorial(id, body));
    }

    // Obtener todos los registros del historial del paciente (GET)
    @GetMapping("/registros/{id}")
    public ResponseEntity<List<PacienteHistorialDTO>> obtenerHistorial(@PathVariable Integer id) {
        // Llamamos al servicio para obtener el historial del paciente
        List<PacienteHistorialDTO> historial = pacienteService.obtenerHistorial(id);

        // Retornamos la lista de registros de historial con un código HTTP 200 (OK)
        return ResponseEntity.ok(historial);
    }

    // Eliminar un registro del historial (DELETE)
    @DeleteMapping("/registros/{registroId}")
    public ResponseEntity<Void> eliminarHistorial(@PathVariable Integer registroId) {
        // Llamamos al servicio para eliminar el historial
        pacienteService.eliminarHistorial(registroId);

        // Retornamos una respuesta 204 No Content indicando que la eliminación fue exitosa
        return ResponseEntity.noContent().build();
    }
    // Endpoint para validar si el paciente existe por su ID
    @GetMapping("/validar/{id}")
    public ResponseEntity<Boolean> validarPaciente(@PathVariable("id") Integer id) {
        boolean pacienteExiste = pacienteService.validarPaciente(id);
        return ResponseEntity.ok(pacienteExiste);  // Retorna true o false dependiendo de si el paciente existe
    }



}