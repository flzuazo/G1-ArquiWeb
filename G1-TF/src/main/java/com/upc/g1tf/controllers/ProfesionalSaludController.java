package com.upc.g1tf.controllers;

import com.upc.g1tf.dtos.PacienteAtendidoDTO;
import com.upc.g1tf.dtos.ProfesionalSaludDTO;
import com.upc.g1tf.interfaces.IProfesionalSaludService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization")
@RequestMapping("/api")
public class ProfesionalSaludController {
    @Autowired
    private IProfesionalSaludService profesionalSaludService;
    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/nuevo_profesionalsalud")
    public ResponseEntity<ProfesionalSaludDTO> registrarProfesional(@Valid @RequestBody ProfesionalSaludDTO profesionalSaludDTO) {
        ProfesionalSaludDTO nuevoProfesional = profesionalSaludService.registrarProfesional(profesionalSaludDTO);
        return ResponseEntity.ok(nuevoProfesional);
    }

    @PostMapping("/public/profesional")
    public ResponseEntity<ProfesionalSaludDTO> registrarProfesionalPublico(@Valid @RequestBody ProfesionalSaludDTO profesionalSaludDTO) {
        ProfesionalSaludDTO nuevoProfesional = profesionalSaludService.registrarProfesional(profesionalSaludDTO);
        return ResponseEntity.ok(nuevoProfesional);
    }

    // HU08 – Consultar Pacientes Atendidos
    @PreAuthorize("hasAnyRole('ADMIN','PROFESIONALSALUD')")
    @GetMapping("/doctor/{id}/pacientes")
    public ResponseEntity<List<PacienteAtendidoDTO>> listarPacientesAtendidos(@PathVariable Integer id) {
        List<PacienteAtendidoDTO> pacientes = profesionalSaludService.listarPacientesAtendidos(id);
        return ResponseEntity.ok(pacientes);
    }
    @GetMapping("/profesionales")
    @PreAuthorize("hasAnyRole('ADMIN','PACIENTE')")
    public ResponseEntity<List<ProfesionalSaludDTO>> listar() {
        return ResponseEntity.ok(profesionalSaludService.listar());
    }

}
