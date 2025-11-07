package com.upc.g1tf.dtos;

import com.upc.g1tf.entities.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaDTO {
    private Integer idConsulta;
    private Integer idPaciente; //Referencia
    private Integer idProfesional;
    private Integer idCentroMedico;//Referencia
    private LocalDate fechaConsulta;
    private java.util.List<DiagnosticoDTO> diagnosticos;
    private java.util.List<RecetaDTO> recetas;
}
