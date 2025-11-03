package com.upc.g1tf.dtos;

import com.upc.g1tf.entities.Consulta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DiagnosticoDTO {
    private Integer idDiagnostico;
    private Integer idConsulta; //referencia
    //private Consulta consulta; //objeto
    private String descripcion;
    private String codigoCIE10;
}