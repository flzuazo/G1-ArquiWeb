package com.upc.g1tf.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PacienteAtendidoDTO {

    private Integer idPaciente;
    private String nombres;
    private String apellidos;
    private String dni;

    private LocalDate fechaUltimaConsulta;
    private String diagnosticoUltimo;
    private Integer idUltimaConsulta; // para “acceso al detalle”

}