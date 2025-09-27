package com.upc.g1tf.repositories;

import com.upc.g1tf.entities.RecetaMedicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecetaMedicamentoRepository extends JpaRepository<RecetaMedicamento, Long> {
}
