package com.upc.g1tf.entities;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
public class RecetaMedicamentoId {
    private Integer idReceta;
    private Integer idMedicamento;
}
