package com.upc.g1tf.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CentroMedicoDTO {
    private Integer idCentro;
    private String nombreCentro;
    private String direccion;
    private String telefono;
}
