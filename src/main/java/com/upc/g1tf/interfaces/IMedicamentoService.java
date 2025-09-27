package com.upc.g1tf.interfaces;

import com.upc.g1tf.dtos.MedicamentoDTO;
import java.util.List;

public interface IMedicamentoService {
    MedicamentoDTO insertarMedicamento(MedicamentoDTO medicamentoDTO);
    List<MedicamentoDTO> listarMedicamentos();
    MedicamentoDTO buscarMedicamentoPorId(Long id);
    MedicamentoDTO modificarMedicamento(MedicamentoDTO medicamentoDTO);
    void eliminarMedicamento(Long id);
}
