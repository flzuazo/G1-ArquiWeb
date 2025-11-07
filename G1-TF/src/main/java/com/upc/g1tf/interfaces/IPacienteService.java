package com.upc.g1tf.interfaces;

import com.upc.g1tf.dtos.PacienteDTO;
import com.upc.g1tf.dtos.PacienteHistorialDTO;
import com.upc.g1tf.dtos.PacienteUpdateDTO;

public interface IPacienteService {
    PacienteDTO registrarPaciente(PacienteDTO pacienteDTO); //HU02
    PacienteDTO actualizarPaciente(Integer idPaciente, PacienteUpdateDTO updateDTO);
    PacienteDTO actualizarHistorial(Integer idPaciente, PacienteHistorialDTO pacientehistorialdto); //HU12
}
