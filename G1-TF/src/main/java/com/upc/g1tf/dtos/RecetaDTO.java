package com.upc.g1tf.dtos;

import com.upc.g1tf.entities.Consulta;
import com.upc.g1tf.entities.RecetaMedicamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecetaDTO {
    private Integer idReceta;
    private Integer idConsulta;//referencia
    //private Consulta consulta;//objeto
    private LocalDate fechaEmision;
    private List<RecetaMedicamentoDTO> recetaMedicamentos;
}