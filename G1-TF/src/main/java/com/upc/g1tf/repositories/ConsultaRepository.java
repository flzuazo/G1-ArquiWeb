package com.upc.g1tf.repositories;

import com.upc.g1tf.dtos.PacienteAtendidoDTO;
import com.upc.g1tf.dtos.ReporteCentroDTO;
import com.upc.g1tf.dtos.ReporteEspecialidadDTO;
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


    // Reporte por centro médico
    @Query("""
        SELECT new com.upc.g1tf.dtos.ReporteCentroDTO(
            cMed.nombreCentro,
            COUNT(cs.idConsulta),
            COUNT(DISTINCT p.idProfesional)
        )
        FROM Consulta cs
        JOIN cs.centroMedico cMed
        JOIN cs.profesional p
        WHERE cs.fechaConsulta BETWEEN :fechaInicio AND :fechaFin
        GROUP BY cMed.nombreCentro
        """)
    List<ReporteCentroDTO> generarReporte(@Param("fechaInicio") LocalDate fechaInicio,
                                          @Param("fechaFin") LocalDate fechaFin);

    // Consulta para obtener las consultas por mes y año
    @Query("SELECT EXTRACT(MONTH FROM c.fechaConsulta) AS mes, EXTRACT(YEAR FROM c.fechaConsulta) AS año, COUNT(c) AS cantidadConsultas "
            + "FROM Consulta c WHERE EXTRACT(YEAR FROM c.fechaConsulta) IN (2024, 2025) "
            + "GROUP BY EXTRACT(MONTH FROM c.fechaConsulta), EXTRACT(YEAR FROM c.fechaConsulta) "
            + "ORDER BY año, mes")
    List<Object[]> obtenerConsultasPorMes();


}