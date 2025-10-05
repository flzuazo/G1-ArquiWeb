package com.upc.g1tf.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecetaMedicamentoDTO {
    private Integer idReceta;
    private Integer idMedicamento;
    private String indicaciones;
}