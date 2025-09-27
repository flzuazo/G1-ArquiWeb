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
    private Long idReceta;
    private Long idMedicamento;
    private String indicaciones;
}
