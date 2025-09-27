package com.upc.g1tf.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConsulta;

    @ManyToOne
    @JoinColumn(name = "idPaciente", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "idProfesional", nullable = false)
    private ProfesionalSalud profesional;

    @ManyToOne
    @JoinColumn(name="idCentro", nullable = false)
    private CentroMedico centroMedico;

    private LocalDate fechaConsulta;
    private String diagnostico;
    private String tratamiento;

    @OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL)
    private java.util.List<Diagnostico> diagnosticos;

    @OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL)
    private java.util.List<Receta> recetas;
}
