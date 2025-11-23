package com.upc.g1tf.services;

import com.upc.g1tf.dtos.ConsultaDTO;
import com.upc.g1tf.entities.*;
import com.upc.g1tf.interfaces.IConsultaService;
import com.upc.g1tf.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaService implements IConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ProfesionalSaludRepository profesionalSaludRepository;

    @Autowired
    private CentroMedicoRepository centroMedicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;


    // ====== Mapper DTO <-> Entity ======
    private ConsultaDTO toDTO(Consulta consulta) {
        ConsultaDTO dto = new ConsultaDTO();
        dto.setIdConsulta(consulta.getIdConsulta());
        dto.setIdPaciente(consulta.getPaciente().getIdPaciente());
        dto.setIdProfesional(consulta.getProfesional().getIdProfesional());
        dto.setIdCentroMedico(consulta.getCentroMedico().getIdCentroMedico());
        dto.setFechaConsulta(consulta.getFechaConsulta());
        dto.setDiagnostico(consulta.getDiagnostico());
        dto.setReceta(consulta.getReceta());

        return dto;
    }


    @Transactional
    @Override
    public ConsultaDTO insertarConsulta(ConsultaDTO dto) {
        // === Buscar entidades relacionadas ===
        Paciente paciente = pacienteRepository.findById(dto.getIdPaciente())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado: " + dto.getIdPaciente()));

        ProfesionalSalud profesional = profesionalSaludRepository.findById(dto.getIdProfesional())
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado: " + dto.getIdProfesional()));

        CentroMedico centro = centroMedicoRepository.findById(dto.getIdCentroMedico())
                .orElseThrow(() -> new RuntimeException("Centro médico no encontrado: " + dto.getIdCentroMedico()));

        // === Crear consulta base ===
        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setProfesional(profesional);
        consulta.setCentroMedico(centro);
        consulta.setFechaConsulta(dto.getFechaConsulta());
        consulta.setDiagnostico(dto.getDiagnostico());
        consulta.setReceta(dto.getReceta());
        // Copia final para lambdas
        final Consulta consultaFinal = consulta;

        consulta = consultaRepository.save(consulta);

        return toDTO(consulta);
    }

    @Override
    public List<ConsultaDTO> listarConsultas() {
        return consultaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ConsultaDTO buscarConsultaPorId(Integer id) {
        return consultaRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Consulta no encontrada: " + id));
    }

    @Transactional
    @Override
    public ConsultaDTO modificarConsulta(ConsultaDTO consultaDTO) {
        Consulta consulta = consultaRepository.findById(consultaDTO.getIdConsulta())
                .orElseThrow(() -> new RuntimeException("Consulta no encontrada: " + consultaDTO.getIdConsulta()));

        consulta.setFechaConsulta(consultaDTO.getFechaConsulta());
        //consulta.setDiagnostico(consultaDTO.getDiagnostico());
        //consulta.setTratamiento(consultaDTO.getTratamiento());

        consulta = consultaRepository.save(consulta);
        return toDTO(consulta);
    }

    @Transactional
    @Override
    public void eliminarConsulta(Integer id) {
        if (consultaRepository.existsById(id)) {
            consultaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Consulta no encontrada: " + id);
        }
    }

    @Override
    public List<ConsultaDTO> listarHistorialPorPaciente(Integer pacienteId) {
        return consultaRepository.findByPacienteIdPacienteOrderByFechaConsultaDesc(pacienteId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
