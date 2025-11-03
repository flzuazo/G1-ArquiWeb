package com.upc.g1tf.controllers;

import com.upc.g1tf.dtos.ConsultaDTO;
import com.upc.g1tf.interfaces.IConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
@RequestMapping("/api")
public class ConsultaController {

    @Autowired
    private IConsultaService consultaService;

    @PreAuthorize("hasRole('PROFESIONALSALUD')")
    @PostMapping("/nueva_consulta")
    public ConsultaDTO insertarConsulta(@RequestBody ConsultaDTO consultaDTO) {
        return consultaService.insertarConsulta(consultaDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESIONALSALUD')")
    @GetMapping("/lsitar_consultas")
    public List<ConsultaDTO> listarConsultas() {
        return consultaService.listarConsultas();
    }

    @GetMapping("/buscar_consulta/{id}")
    public ConsultaDTO buscarConsulta(@PathVariable Integer id) {
        return consultaService.buscarConsultaPorId(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESIONALSALUD')")
    @PutMapping("/modificar_consulta/{id}")
    public ConsultaDTO modificarConsulta(@PathVariable Integer id, @RequestBody ConsultaDTO consultaDTO) {
        consultaDTO.setIdConsulta(id);
        return consultaService.modificarConsulta(consultaDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESIONALSALUD')")
    @DeleteMapping("/eliminar_consulta/{id}")
    public void eliminarConsulta(@PathVariable Integer id) {
        consultaService.eliminarConsulta(id);
    }

    @GetMapping("/historial_paciente/{pacienteId}")
    public List<ConsultaDTO> listarHistorialPorPaciente(@PathVariable Integer pacienteId) {
        return consultaService.listarHistorialPorPaciente(pacienteId);
    }
}

