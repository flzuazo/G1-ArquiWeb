package com.upc.g1tf.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PacienteHistorialDTO {
    private Integer registroId;
    private String alergias;
    private String antecedentes;
    private LocalDateTime fechaUltimaActualizacion;
}




