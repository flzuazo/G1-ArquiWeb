package com.upc.g1tf.controllers;

import com.upc.g1tf.dtos.ProfesionalSaludDTO;
import com.upc.g1tf.interfaces.IProfesionalSaludService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProfesionalSaludController {
    @Autowired
    private IProfesionalSaludService profesionalSaludService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/nuevo_profesionalsalud")
    public ResponseEntity<ProfesionalSaludDTO> registrarProfesional(@Valid @RequestBody ProfesionalSaludDTO profesionalSaludDTO) {
        ProfesionalSaludDTO nuevoProfesional = profesionalSaludService.registrarProfesional(profesionalSaludDTO);
        return ResponseEntity.ok(nuevoProfesional);
    }
}
