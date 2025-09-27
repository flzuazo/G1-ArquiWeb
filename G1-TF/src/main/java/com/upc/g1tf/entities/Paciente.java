package com.upc.g1tf.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPaciente;

    private String nombres;
    private String apellidos;

    @Column(length = 8, unique = true, nullable = false)
    private String dni;

    private LocalDate fechaNacimiento;
    private Character sexo;
    private String direccion;
    private String telefono;
    private String email;
    private String tipoSangre;

    @Column(length = 500)
    private String alergias;

}
