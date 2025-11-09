package com.upc.g1tf.interfaces;

import com.upc.g1tf.dtos.PacienteAtendidoDTO;
import com.upc.g1tf.dtos.ProfesionalSaludDTO;

import java.util.List;

public interface IProfesionalSaludService {
    ProfesionalSaludDTO registrarProfesional(ProfesionalSaludDTO profesionalSaludDTO); // HU006
    List<PacienteAtendidoDTO> listarPacientesAtendidos(Integer idProfesional); // HU08
}


