package com.upc.g1tf.repositories;

import com.upc.g1tf.entities.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {
    List<Consulta> findByPacienteIdPacienteOrderByFechaConsultaDesc(Integer pacienteId);

    @Query("""
        SELECT p.idPaciente, p.nombres, p.apellidos, p.dni,
               c.fechaConsulta, c.diagnostico, c.idConsulta
        FROM Consulta c
        JOIN c.paciente p
        WHERE c.profesional.idProfesional = :idDoc
          AND c.fechaConsulta = (
              SELECT MAX(c2.fechaConsulta)
              FROM Consulta c2
              WHERE c2.paciente.idPaciente = p.idPaciente
                AND c2.profesional.idProfesional = :idDoc
          )
        ORDER BY p.apellidos, p.nombres
        """)
    List<Object[]> findPacientesAtendidos(@Param("idDoc") Integer idProfesional);
}