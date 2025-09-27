package com.upc.g1tf.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecetaMedicamento {
    @EmbeddedId
    private RecetaMedicamentoId id;

    @MapsId("idReceta") // usa la parte idReceta de id
    @ManyToOne
    @JoinColumn(name = "id_receta") // coincide con la columna real
    private Receta receta;

    @MapsId("idMedicamento") // usa la parte idMedicamento de id
    @ManyToOne
    @JoinColumn(name = "id_medicamento") // coincide con la columna real
    private Medicamento medicamento;

    private String indicaciones;
}
