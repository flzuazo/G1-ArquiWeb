package com.upc.g1tf.repositories;

import com.upc.g1tf.entities.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecetaRepository extends JpaRepository<Receta,Integer> {

}
