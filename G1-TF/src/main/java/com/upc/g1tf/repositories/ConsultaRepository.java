package com.upc.g1tf.repositories;

import com.upc.g1tf.dtos.ReporteCentroDTO;
import com.upc.g1tf.entities.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {
    List<Consulta> findByPacienteIdPacienteOrderByFechaConsultaDesc(Integer pacienteId);
    @Query("SELECT new com.upc.g1tf.dtos.ReporteCentroDTO(c.nombre, COUNT(cs.idConsulta), COUNT(DISTINCT p.idProfesional)) " +
            "FROM Consulta cs " +
            "JOIN cs.centroMedico c " +
            "JOIN cs.profesional p " +
            "WHERE cs.fechaConsulta BETWEEN :fechaInicio AND :fechaFin " +
            "GROUP BY c.nombre")
    List<ReporteCentroDTO> generarReporte(@Param("fechaInicio") LocalDate fechaInicio,
                                          @Param("fechaFin") LocalDate fechaFin);
}
