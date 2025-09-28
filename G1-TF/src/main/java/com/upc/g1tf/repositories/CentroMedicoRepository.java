package com.upc.g1tf.repositories;

import com.upc.g1tf.entities.CentroMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CentroMedicoRepository extends JpaRepository<CentroMedico, Integer> {
}
