package com.upc.g1tf.dtos;

import com.upc.g1tf.entities.Paciente;
import com.upc.g1tf.entities.ProfesionalSalud;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Integer idUsuario;
    private String username;
    private String password;
    private String rol;
    private Integer idPaciente;//referencia
    //private Paciente paciente; //objeto
    private Integer idProfesional;//referencia
    //private ProfesionalSalud profesional;//objeto
}
