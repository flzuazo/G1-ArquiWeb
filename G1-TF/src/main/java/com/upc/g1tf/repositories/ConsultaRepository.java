package com.upc.g1tf.repositories;

import com.upc.g1tf.dtos.ReporteCentroDTO;
import com.upc.g1tf.dtos.ReporteEspecialidadDTO;
import com.upc.g1tf.entities.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {
    List<Consulta> findByPacienteIdPacienteOrderByFechaConsultaDesc(Integer pacienteId);

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

    @Query("SELECT new com.upc.g1tf.dtos.ReporteCentroDTO(c.nombreCentro, COUNT(cs.idConsulta), COUNT(DISTINCT p.idProfesional)) " +
            "FROM Consulta cs " +
            "JOIN cs.centroMedico c " +
            "JOIN cs.profesional p " +
            "WHERE cs.fechaConsulta BETWEEN :fechaInicio AND :fechaFin " +
            "GROUP BY c.nombreCentro")
    List<ReporteCentroDTO> generarReporte(@Param("fechaInicio") LocalDate fechaInicio,
                                          @Param("fechaFin") LocalDate fechaFin);

    @Query("SELECT new com.upc.g1tf.dtos.ReporteEspecialidadDTO(p.especialidad, COUNT(DISTINCT c.paciente.id), COUNT(c)) " +
            "FROM Consulta c JOIN c.profesionalSalud p " +
            "WHERE c.fecha BETWEEN :inicio AND :fin " +
            "GROUP BY p.especialidad")
    List<ReporteEspecialidadDTO> obtenerReportePorEspecialidad(
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin);

}