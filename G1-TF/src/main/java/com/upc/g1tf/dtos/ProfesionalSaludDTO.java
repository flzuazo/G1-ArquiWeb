package com.upc.g1tf.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfesionalSaludDTO {
    private Integer idProfesional;
    private String nombres;
    private String apellidos;
    private String especialidad;
    private String colegiatura;
    private String email;
    private String telefono;
}
