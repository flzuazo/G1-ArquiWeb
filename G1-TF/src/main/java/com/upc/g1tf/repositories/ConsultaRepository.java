package com.upc.g1tf.repositories;

import com.upc.g1tf.dtos.PacienteAtendidoDTO;
import com.upc.g1tf.entities.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {

    // 🔹 HU05 – Historial médico completo del paciente
    @Query("""
        SELECT c FROM Consulta c
        LEFT JOIN FETCH c.profesional p
        LEFT JOIN FETCH c.centroMedico cm
        WHERE c.paciente.idPaciente = :idPaciente
        ORDER BY c.fechaConsulta DESC
    """)
    List<Consulta> findHistorialByPacienteIdWithAllData(@Param("idPaciente") Integer idPaciente);

    List<Consulta> findByPacienteIdPacienteOrderByFechaConsultaDesc(Integer pacienteId);

    // 🔹 HU08 – Pacientes atendidos por doctor (versión mejorada)
        @Query("""
        SELECT new com.upc.g1tf.dtos.PacienteAtendidoDTO(
            p.idPaciente,
            p.nombres,
            p.apellidos,
            p.dni,
            c.fechaConsulta,
            c.diagnostico,
            c.idConsulta
        )
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
    List<PacienteAtendidoDTO> findPacientesAtendidos(@Param("idDoc") Integer idProfesional);

}