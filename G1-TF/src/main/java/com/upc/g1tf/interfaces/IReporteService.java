package com.upc.g1tf.interfaces;

import com.upc.g1tf.dtos.ReporteCentroDTO;
import com.upc.g1tf.dtos.ReporteEspecialidadDTO;

import java.time.LocalDate;
import java.util.List;

public interface IReporteService {
    List<ReporteCentroDTO> generarReporte(LocalDate fechaInicio, LocalDate fechaFin);
    List<ReporteEspecialidadDTO> obtenerReportePorEspecialidad(LocalDate inicio, LocalDate fin);
}
