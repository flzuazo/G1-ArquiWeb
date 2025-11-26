package com.upc.g1tf.interfaces;

import com.upc.g1tf.dtos.HistorialMedicoDTO;
import com.upc.g1tf.dtos.PacienteDTO;
import com.upc.g1tf.dtos.PacienteHistorialDTO;
import com.upc.g1tf.dtos.PacienteUpdateDTO;

import java.util.List;

public interface IPacienteService {
    PacienteDTO registrarPaciente(PacienteDTO pacienteDTO); //HU02
    PacienteDTO actualizarPaciente(Integer idPaciente, PacienteUpdateDTO updateDTO);
    List<PacienteDTO> listarPacientes();
    List<HistorialMedicoDTO> listarHistorialPorPaciente(Integer idPaciente);//HU05
    PacienteDTO actualizarHistorial(Integer idPaciente, PacienteHistorialDTO pacientehistorialdto); //HU12
    PacienteDTO obtenerPaciente(Integer id);
    List<PacienteHistorialDTO> obtenerHistorial(Integer registroId);
    void eliminarHistorial(Integer idPaciente);
    boolean validarPaciente(Integer idPaciente);
}

