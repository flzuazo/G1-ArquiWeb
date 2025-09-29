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
public class PacienteHistorialDTO {
    private String alergias;

    private List<ConsultaDTO> consultas;

    @Getter
    @Setter
    public static class ConsultaHistorialDTO {
        private LocalDate fechaConsulta;
        private String diagnostico;
        private String tratamiento;
    }
}
