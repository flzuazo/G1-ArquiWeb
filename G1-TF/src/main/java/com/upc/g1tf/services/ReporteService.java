package com.upc.g1tf.services;

import com.upc.g1tf.dtos.ReporteCentroDTO;
import com.upc.g1tf.interfaces.IReporteService;
import com.upc.g1tf.repositories.ConsultaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReporteService implements IReporteService {

    private final ConsultaRepository consultaRepository;

    public ReporteService(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    @Override
    public List<ReporteCentroDTO> generarReporte(LocalDate fechaInicio, LocalDate fechaFin) {
        return consultaRepository.generarReporte(fechaInicio, fechaFin);
    }
}
