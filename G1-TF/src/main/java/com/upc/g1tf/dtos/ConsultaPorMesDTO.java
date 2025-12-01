package com.upc.g1tf.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaPorMesDTO {
    private int mes;
    private int año;
    private long cantidadConsultas;
}
