package com.upc.g1tf.interfaces;

import com.upc.g1tf.dtos.PacienteAtendidoDTO;
import com.upc.g1tf.dtos.ProfesionalSaludDTO;
import com.upc.g1tf.dtos.ReporteEspecialidadDTO;

import java.util.List;

public interface IProfesionalSaludService {
    ProfesionalSaludDTO registrarProfesional(ProfesionalSaludDTO profesionalSaludDTO); // HU006
    List<PacienteAtendidoDTO> listarPacientesAtendidos(Integer idProfesional); // HU08
    List<ProfesionalSaludDTO> listar(); //HU15
    // Nuevo: reporte de cantidad de profesionales por especialidad
    List<ReporteEspecialidadDTO> reportePorEspecialidad();
}


