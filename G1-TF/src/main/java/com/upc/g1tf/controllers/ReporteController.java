package com.upc.g1tf.controllers;

import com.upc.g1tf.dtos.ReporteCentroDTO;
import com.upc.g1tf.dtos.ReporteEspecialidadDTO;
import com.upc.g1tf.services.ReporteService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/centros")
    public List<ReporteCentroDTO> generarReporte(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return reporteService.generarReporte(fechaInicio, fechaFin);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/especialidades")
    public ResponseEntity<?> reportePorEspecialidad(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam(value = "format", required = false) String format) {

        List<ReporteEspecialidadDTO> reporte = reporteService.obtenerReportePorEspecialidad(fechaInicio, fechaFin);

        if ("csv".equalsIgnoreCase(format)) {
            StringBuilder csv = new StringBuilder("especialidad,pacientes,consultas\n");
            for (ReporteEspecialidadDTO r : reporte) {
                csv.append(r.getEspecialidad()).append(",")
                        .append(r.getPacientes()).append(",")
                        .append(r.getConsultas()).append("\n");
            }
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_especialidades.csv")
                    .contentType(MediaType.parseMediaType("text/csv"))
                    .body(csv.toString());
        }

        return ResponseEntity.ok(reporte);
    }

}