package com.upc.g1tf.repositories;

import com.upc.g1tf.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PacienteRepository extends JpaRepository<Paciente,Integer> {
    Optional<Paciente> findByDni(String dni); // Para validar
}