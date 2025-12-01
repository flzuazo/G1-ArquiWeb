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
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
@RequestMapping("/api")
public class PacienteController {
    @Autowired
    private IPacienteService pacienteService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/nuevo_paciente")// HU02
    public ResponseEntity<PacienteDTO> registrarPaciente(@Valid @RequestBody PacienteDTO pacienteDTO) {
        PacienteDTO nuevoPaciente = pacienteService.registrarPaciente(pacienteDTO);
        return ResponseEntity.ok(nuevoPaciente);
    }
    @PreAuthorize("hasRole('ADMIN')")
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

    @GetMapping("/listar_pacientes")
    public ResponseEntity<List<PacienteDTO>> listarPacientes() {
        List<PacienteDTO> lista = pacienteService.listarPacientes();
        return ResponseEntity.ok(lista);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PROFESIONALSALUD')")
    @GetMapping("/paciente_dni/{dni}")
    public ResponseEntity<?> buscarPorDni(@PathVariable String dni) {
        return pacienteService.buscarPorDni(dni)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
