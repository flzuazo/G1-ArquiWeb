package com.upc.g1tf.controllers;

import com.upc.g1tf.dtos.HistorialMedicoDTO;
import com.upc.g1tf.dtos.PacienteDTO;
import com.upc.g1tf.dtos.PacienteHistorialDTO;
import com.upc.g1tf.dtos.PacienteUpdateDTO;
import com.upc.g1tf.interfaces.IPacienteService;
import jakarta.validation.Valid;
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

    @GetMapping("/pacientes/{id}/historial") // HU05 – Visualizar Historial Médico
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_PROFESIONALSALUD', 'ROLE_PACIENTE')")
    public ResponseEntity<List<HistorialMedicoDTO>> listarHistorialPorPaciente(@PathVariable Integer id) {
        List<HistorialMedicoDTO> historial = pacienteService.listarHistorialPorPaciente(id);
        return ResponseEntity.ok(historial);
    }
    @GetMapping("/pacientes/{id}")
    public ResponseEntity<PacienteDTO> obtenerPaciente(@PathVariable Integer id) {
        PacienteDTO paciente = pacienteService.obtenerPaciente(id);
        return ResponseEntity.ok(paciente);
    }


    // ===== HU12 – Actualizar Antecedentes =====
    @PutMapping("/paciente/historial/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PROFESIONALSALUD')")
    public ResponseEntity<PacienteDTO> actualizarHistorial(
            @PathVariable Integer id,
            @Valid @RequestBody PacienteHistorialDTO body) { // <-- este
        return ResponseEntity.ok(pacienteService.actualizarHistorial(id, body));
    }

    // ======================================================
//  HU12 – Validar si un paciente existe (para botón Buscar)
//  GET /api/pacientes/validar/${id}
// ======================================================
    @GetMapping("/paciente/validar/{id}")
    public ResponseEntity<Boolean> validarPaciente(@PathVariable Integer id) {
        boolean existe = pacienteService.validarPaciente(id);
        return ResponseEntity.ok(existe);
    }



    // ======================================================
//  HU12 – Listar historial completo para la tabla
//  GET /api/pacientes/registros/${id}
// ======================================================
    @GetMapping("/paciente/registros/{id}")
    public ResponseEntity<List<PacienteHistorialDTO>> obtenerHistorial(@PathVariable Integer id) {
        List<PacienteHistorialDTO> lista = pacienteService.obtenerHistorial(id);
        return ResponseEntity.ok(lista);
    }
    // ======================================================
//  HU12 – Eliminar historial del paciente
//  DELETE /api/registros/${registroId}
// ======================================================
    @DeleteMapping("/paciente/historial/{registroId}")
    public ResponseEntity<Void> eliminarHistorial(@PathVariable Integer registroId) {

        pacienteService.eliminarHistorial(registroId);
        return ResponseEntity.noContent().build();
    }


}
