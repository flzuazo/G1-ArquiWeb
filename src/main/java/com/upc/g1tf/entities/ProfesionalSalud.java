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
public class ProfesionalSalud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProfesional;

    private String nombres;
    private String apellidos;
    private String especialidad;

    @Column(unique = true, nullable = false)
    private String colegiatura;

    private String email;
    private String telefono;
}
