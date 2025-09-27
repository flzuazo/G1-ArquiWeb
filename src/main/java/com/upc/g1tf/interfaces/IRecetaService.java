package com.upc.g1tf.interfaces;

import com.upc.g1tf.dtos.RecetaDTO;
import java.util.List;

public interface IRecetaService {
    RecetaDTO insertarReceta(RecetaDTO recetaDTO);
    List<RecetaDTO> listarRecetas();
    RecetaDTO buscarRecetaPorId(Long id);
    RecetaDTO modificarReceta(RecetaDTO recetaDTO);
    void eliminarReceta(Long id);
}
