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
public class Diagnostico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDiagnostico;

    @ManyToOne
    @JoinColumn(name = "idConsulta", nullable = false)
    private Consulta consulta;

    private String descripcion;
    private String codigoCIE10; // standard internacional de enfermedades
}
