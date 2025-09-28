package com.upc.g1tf.interfaces;

import com.upc.g1tf.dtos.ConsultaDTO;
import java.util.List;

public interface IConsultaService {
    ConsultaDTO insertarConsulta(ConsultaDTO consultaDTO);
    List<ConsultaDTO> listarConsultas();
    ConsultaDTO buscarConsultaPorId(Integer id);
    ConsultaDTO modificarConsulta(ConsultaDTO consultaDTO);
    void eliminarConsulta(Integer id);
    List<ConsultaDTO> listarHistorialPorPaciente(Integer pacienteId);
}
