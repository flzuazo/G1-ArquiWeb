package com.upc.g1tf.services;

import com.upc.g1tf.dtos.ConsultaDTO;
import com.upc.g1tf.entities.Consulta;
import com.upc.g1tf.entities.Paciente;
import com.upc.g1tf.interfaces.IConsultaService;
import com.upc.g1tf.repositories.ConsultaRepository;
import com.upc.g1tf.repositories.PacienteRepository;
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
    private PacienteRepository pacienteRepository;

    private ConsultaDTO toDTO(Consulta consulta) {
        ConsultaDTO dto = new ConsultaDTO();
        dto.setIdConsulta(consulta.getIdConsulta());
        dto.setIdPaciente(consulta.getPaciente().getIdPaciente());
        dto.setIdProfesional(consulta.getProfesional().getIdProfesional());
        dto.setIdCentroMedico(consulta.getCentroMedico().getIdCentro());
        dto.setFechaConsulta(consulta.getFechaConsulta());
        dto.setDiagnostico(consulta.getDiagnostico());
        dto.setTratamiento(consulta.getTratamiento());
        return dto;
    }

    private Consulta toEntity(ConsultaDTO dto) {
        Consulta consulta = new Consulta();

        consulta.setFechaConsulta(dto.getFechaConsulta());
        consulta.setDiagnostico(dto.getDiagnostico());
        consulta.setTratamiento(dto.getTratamiento());
        return consulta;
    }

    @Transactional
    @Override
    public ConsultaDTO insertarConsulta(ConsultaDTO consultaDTO) {
        Consulta consulta = toEntity(consultaDTO);

        consulta = consultaRepository.save(consulta);
        return toDTO(consulta);
    }

    @Override
    public List<ConsultaDTO> listarConsultas() {
        return consultaRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
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
        consulta.setDiagnostico(consultaDTO.getDiagnostico());
        consulta.setTratamiento(consultaDTO.getTratamiento());
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


    public List<ConsultaDTO> listarHistorialPorPaciente(Integer pacienteId) {
        Paciente paciente = pacienteRepository.findById(pacienteId).orElse(null);
        if (paciente == null) return List.of();
        return consultaRepository.findByPacienteIdPacienteOrderByFechaConsultaDesc(paciente.getIdPaciente())
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}