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
public class HistorialMedicoDTO {
    private Integer idConsulta;
    private LocalDate fechaConsulta;

    private String doctor;
    private String especialidad;
    private String centroMedico;

    private String diagnostico;
    private String receta;
}
