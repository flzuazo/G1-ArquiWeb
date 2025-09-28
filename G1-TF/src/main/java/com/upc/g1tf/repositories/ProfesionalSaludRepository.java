package com.upc.g1tf.repositories;

import com.upc.g1tf.entities.ProfesionalSalud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ProfesionalSaludRepository extends JpaRepository<ProfesionalSalud,Integer> {
    Optional<ProfesionalSalud> findByColegiatura(String colegiatura);// Para validar
    Optional<ProfesionalSalud> findByEmail(String email);// Para validar
}
