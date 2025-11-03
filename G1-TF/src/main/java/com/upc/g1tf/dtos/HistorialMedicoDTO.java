package com.upc.g1tf.dtos;
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
public class HistorialMedicoDTO {
    private Integer idConsulta;
    private LocalDate fechaConsulta;

    private String doctor;
    private String especialidad;
    private String centroMedico;

    private List<DiagnosticoDTO> diagnosticos;
    private List<RecetaDTO> recetas;
}
