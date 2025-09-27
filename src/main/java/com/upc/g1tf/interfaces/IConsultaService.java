package com.upc.g1tf.interfaces;

import com.upc.g1tf.dtos.ConsultaDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface IConsultaService {
    ConsultaDTO insertarConsulta(ConsultaDTO consultaDTO);
    List<ConsultaDTO> listarConsultas();
    ConsultaDTO buscarConsultaPorId(Long id);
    ConsultaDTO modificarConsulta(ConsultaDTO consultaDTO);
    void eliminarConsulta(Long id);

    List<ConsultaDTO> listarHistorialPorPaciente(Integer pacienteId);
}
