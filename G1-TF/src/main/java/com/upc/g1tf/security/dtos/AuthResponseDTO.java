
package com.upc.g1tf.security.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Setter
@Getter
@lombok.Data
public class AuthResponseDTO {
    private String jwt;
    private Set<String> roles;
    private Integer idPaciente;
    private Integer idProfesionalSalud;
}
