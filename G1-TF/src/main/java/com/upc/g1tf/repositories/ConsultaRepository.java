package com.upc.g1tf.repositories;

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
        SELECT DISTINCT c FROM Consulta c
        LEFT JOIN FETCH c.diagnosticos d
        LEFT JOIN FETCH c.recetas r
        LEFT JOIN FETCH r.recetaMedicamentos rm
        LEFT JOIN FETCH rm.medicamento m
        LEFT JOIN FETCH c.profesional p
        LEFT JOIN FETCH c.centroMedico cm
        WHERE c.paciente.idPaciente = :idPaciente
        ORDER BY c.fechaConsulta DESC
    """)
    List<Consulta> findHistorialByPacienteIdWithAllData(@Param("idPaciente") Integer idPaciente);

    List<Consulta> findByPacienteIdPacienteOrderByFechaConsultaDesc(Integer pacienteId);

    // HU08 – Pacientes atendidos por doctor
    @Query("""
        SELECT p.idPaciente, p.nombres, p.apellidos, p.dni,
               c.fechaConsulta, c.idConsulta
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

    // Reporte por especialidad
    @Query("""
        SELECT new com.upc.g1tf.dtos.ReporteEspecialidadDTO(
            p.especialidad,
            COUNT(DISTINCT c.paciente.idPaciente),
            COUNT(c)
        )
        FROM Consulta c
        JOIN c.profesional p
        WHERE c.fechaConsulta BETWEEN :inicio AND :fin
        GROUP BY p.especialidad
        """)
    List<ReporteEspecialidadDTO> obtenerReportePorEspecialidad(
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin);
}