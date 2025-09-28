package com.upc.g1tf.entities;

import jakarta.persistence.Column;
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
    @Column(name = "id_receta")
    private Integer idReceta;

    @Column(name = "id_medicamento")
    private Integer idMedicamento;
}
