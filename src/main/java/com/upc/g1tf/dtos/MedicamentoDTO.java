package com.upc.g1tf.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicamentoDTO {
    private Long idMedicamento;
    private String nombre;
    private String descripcion;
    private String dosis;
}
