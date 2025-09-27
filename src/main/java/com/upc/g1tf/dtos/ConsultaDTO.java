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
    private Long idConsulta;
    private Long idPaciente; //Referencia
    //private Paciente paciente;//objeto
    private Long idProfesional;
    //private ProfesionalSalud profesional; //Objeto
    private Long idCentroMedico;//Referencia
    //private CentroMedico centroMedico; //Objeto
    private LocalDate fechaConsulta;
    private String diagnostico;
    private String tratamiento;
    private java.util.List<Diagnostico> diagnosticos;
    private java.util.List<Receta> recetas;
}
