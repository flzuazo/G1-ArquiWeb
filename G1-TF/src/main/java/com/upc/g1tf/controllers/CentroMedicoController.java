package com.upc.g1tf.controllers;


import com.upc.g1tf.dtos.CentroMedicoDTO;
import com.upc.g1tf.dtos.PacienteDTO;
import com.upc.g1tf.entities.CentroMedico;
import com.upc.g1tf.interfaces.ICentroMedicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CentroMedicoController {

    @Autowired
    private ICentroMedicoService centroMedicoService;

    // ===== Registrar Centro Médico =====
    @PostMapping("/nuevo_centro_medico")
    public ResponseEntity<CentroMedicoDTO>registrarCentroMedico(@Valid @RequestBody CentroMedicoDTO centromedicoDTO){
        CentroMedicoDTO nuevoCentroMedico = centroMedicoService.registrarCentroMedico(centromedicoDTO);
        return ResponseEntity.ok(nuevoCentroMedico);
    }


}
