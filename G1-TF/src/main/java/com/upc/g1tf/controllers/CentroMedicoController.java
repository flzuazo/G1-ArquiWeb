package com.upc.g1tf.controllers;


import com.upc.g1tf.dtos.CentroMedicoDTO;
import com.upc.g1tf.dtos.PacienteDTO;
import com.upc.g1tf.entities.CentroMedico;
import com.upc.g1tf.interfaces.ICentroMedicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
@RequestMapping("/api")
public class CentroMedicoController {

    @Autowired
    private ICentroMedicoService centroMedicoService;

    // ===== Registrar Centro Médico =====
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/nuevo_centro_medico")
    public ResponseEntity<CentroMedicoDTO>registrarCentroMedico(@Valid @RequestBody CentroMedicoDTO centromedicoDTO){
        CentroMedicoDTO nuevoCentroMedico = centroMedicoService.registrarCentroMedico(centromedicoDTO);
        return ResponseEntity.ok(nuevoCentroMedico);
    }


}
