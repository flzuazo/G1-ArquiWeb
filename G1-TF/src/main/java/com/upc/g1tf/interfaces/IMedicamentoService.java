package com.upc.g1tf.interfaces;

import com.upc.g1tf.dtos.MedicamentoDTO;
import com.upc.g1tf.entities.CentroMedico;
import com.upc.g1tf.entities.Diagnostico;
import com.upc.g1tf.entities.Paciente;
import com.upc.g1tf.entities.Receta;

import java.util.List;

public interface IMedicamentoService {
    MedicamentoDTO insertarMedicamento(MedicamentoDTO medicamentoDTO);
    List<MedicamentoDTO> listarMedicamentos();
    MedicamentoDTO buscarMedicamentoPorId(Long id);
    MedicamentoDTO modificarMedicamento(MedicamentoDTO medicamentoDTO);
    void eliminarMedicamento(Long id);
}