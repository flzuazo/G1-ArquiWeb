package com.upc.g1tf.repositories;
import com.upc.g1tf.entities.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Integer> {

    /**
     * HU14: Busca un medicamento por su nombre y dosis para validar duplicados.
     */
    Optional<Medicamento> findByNombreAndDosis(String nombre, String dosis);
}