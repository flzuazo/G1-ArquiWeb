package com.upc.g1tf.repositories;

import com.upc.g1tf.entities.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    List<Consulta> findByPacienteIdPacienteOrderByFechaConsultaDesc(Long idPaciente);
}
