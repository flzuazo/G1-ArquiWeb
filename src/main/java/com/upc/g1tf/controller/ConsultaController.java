package com.upc.g1tf.controller;

import com.upc.g1tf.dtos.ConsultaDTO;
import com.upc.g1tf.interfaces.IConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    @Autowired
    private IConsultaService consultaService;

    @PostMapping
    public ConsultaDTO insertarConsulta(@RequestBody ConsultaDTO consultaDTO) {
        return consultaService.insertarConsulta(consultaDTO);
    }

    @GetMapping
    public List<ConsultaDTO> listarConsultas() {
        return consultaService.listarConsultas();
    }

    @GetMapping("/{id}")
    public ConsultaDTO buscarConsulta(@PathVariable Long id) {
        return consultaService.buscarConsultaPorId(id);
    }

    @PutMapping
    public ConsultaDTO modificarConsulta(@RequestBody ConsultaDTO consultaDTO) {
        return consultaService.modificarConsulta(consultaDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminarConsulta(@PathVariable Long id) {
        consultaService.eliminarConsulta(id);
    }

    @GetMapping("/historial/{pacienteId}")
    public List<ConsultaDTO> listarHistorialPorPaciente(@PathVariable Integer pacienteId) {
        return consultaService.listarHistorialPorPaciente(pacienteId);
    }
}
