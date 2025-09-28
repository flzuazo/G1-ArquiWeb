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

    @ManyToOne
    @MapsId("idReceta")
    @JoinColumn(name = "idReceta")
    private Receta receta;

    @ManyToOne
    @MapsId("idMedicamento")
    @JoinColumn(name = "idMedicamento")
    private Medicamento medicamento;

    private String indicaciones;
}
