package com.upc.g1tf.services;

import com.upc.g1tf.dtos.*;
import com.upc.g1tf.entities.CentroMedico;
import com.upc.g1tf.entities.Consulta;
import com.upc.g1tf.entities.Paciente;
import com.upc.g1tf.entities.ProfesionalSalud;
import com.upc.g1tf.interfaces.IPacienteService;
import com.upc.g1tf.repositories.CentroMedicoRepository;
import com.upc.g1tf.repositories.ConsultaRepository;
import com.upc.g1tf.repositories.PacienteRepository;
import com.upc.g1tf.repositories.ProfesionalSaludRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PacienteService implements IPacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private CentroMedicoRepository centromedicoRepository;          // NEW
    @Autowired
    private ProfesionalSaludRepository profesionalsaludRepository;  // NEW


    // ===== HU02 – Registrar Paciente =====
    @Override
    public PacienteDTO registrarPaciente(PacienteDTO pacienteDTO) {
        if (pacienteDTO.getNombres() == null || pacienteDTO.getApellidos() == null ||
                pacienteDTO.getDni() == null || pacienteDTO.getFechaNacimiento() == null ||
                pacienteDTO.getSexo() == null|| pacienteDTO.getAntecedentes()==null) {
            throw new ValidationException("Todos los campos obligatorios deben estar completos.");
        }

        // Validar duplicidad de DNI
        if (pacienteRepository.findByDni(pacienteDTO.getDni()).isPresent()) {
            throw new ValidationException("El DNI ya está registrado.");
        }

        // Convertir DTO a Entidad
        Paciente paciente = modelMapper.map(pacienteDTO, Paciente.class);

        // Guardar en BD
        paciente.setIdPaciente(null);
        Paciente pacienteGuardado = pacienteRepository.save(paciente);

        // Retornar DTO
        return modelMapper.map(pacienteGuardado, PacienteDTO.class);
    }

    // ===== HU10 – Actualizar Paciente =====
    @Override
    public PacienteDTO actualizarPaciente(Integer idPaciente, PacienteUpdateDTO updateDTO) {
        // Buscar Paciente
        Paciente paciente = pacienteRepository.findById(idPaciente)
                .orElseThrow(()-> new ValidationException("Paciente no encontrado"));

        // Actualizar campos permitidos
        if (updateDTO.getDireccion() != null) {
            paciente.setDireccion(updateDTO.getDireccion());
        }
        if (updateDTO.getTelefono() != null) {
            paciente.setTelefono(updateDTO.getTelefono());
        }
        if (updateDTO.getEmail() != null) {
            paciente.setEmail(updateDTO.getEmail());
        }
        if (updateDTO.getAntecedentes() != null) {
            paciente.setAntecedentes(updateDTO.getAntecedentes());
        }
        Paciente actualizado = pacienteRepository.save(paciente);

        return modelMapper.map(actualizado, PacienteDTO.class);
    }

    // ===== HU12 – Actualizar historial (alergias + antecedentes) =====
    @Override
    public PacienteDTO actualizarHistorial(Integer idPaciente, PacienteHistorialDTO pacientehistorialDTO) {
        Paciente paciente = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado con ID: " + idPaciente));

        if (pacientehistorialDTO.getAlergias() != null) {
            paciente.setAlergias(pacientehistorialDTO.getAlergias());
        }

        if (paciente.getAntecedentes() == null) {
            paciente.setAntecedentes(pacientehistorialDTO.getAntecedentes());
        }

        Paciente actualizado = pacienteRepository.save(paciente);
        return modelMapper.map(actualizado, PacienteDTO.class);
    }

    // ===== HU05 – Visualizar Historial Médico =====
    @Override
    @Transactional(readOnly = true)
    public List<HistorialMedicoDTO> listarHistorialPorPaciente(Integer idPaciente) {

        List<Consulta> consultas = consultaRepository.findByPacienteIdPacienteOrderByFechaConsultaDesc(idPaciente);

        return consultas.stream().map(c -> {
            HistorialMedicoDTO dto = new HistorialMedicoDTO();
            dto.setIdConsulta(c.getIdConsulta());
            dto.setFechaConsulta(c.getFechaConsulta());

            if (c.getProfesional() != null) {
                dto.setDoctor(c.getProfesional().getNombres() + " " + c.getProfesional().getApellidos());
                dto.setEspecialidad(c.getProfesional().getEspecialidad());
            }

            if (c.getCentroMedico() != null)
                dto.setCentroMedico(c.getCentroMedico().getNombreCentro());

            List<DiagnosticoDTO> diagnosticos = (c.getDiagnosticos() == null)
                    ? List.of()
                    : c.getDiagnosticos().stream().map(d -> {
                DiagnosticoDTO dDTO = new DiagnosticoDTO();
                dDTO.setIdDiagnostico(d.getIdDiagnostico());
                dDTO.setIdConsulta(c.getIdConsulta());
                dDTO.setDescripcion(d.getDescripcion());
                dDTO.setCodigoCIE10(d.getCodigoCIE10());
                return dDTO;
            }).collect(Collectors.toList());
            dto.setDiagnosticos(diagnosticos);

            List<RecetaDTO> recetas = (c.getRecetas() == null)
                    ? List.of()
                    : c.getRecetas().stream().map(r -> {
                RecetaDTO rDTO = new RecetaDTO();
                rDTO.setIdReceta(r.getIdReceta());
                rDTO.setIdConsulta(c.getIdConsulta());
                rDTO.setFechaEmision(r.getFechaEmision());

                List<RecetaMedicamentoDTO> meds = (r.getRecetaMedicamentos() == null)
                        ? List.of()
                        : r.getRecetaMedicamentos().stream().map(RM -> {
                    RecetaMedicamentoDTO rmDTO = new RecetaMedicamentoDTO();
                    rmDTO.setIdReceta(RM.getReceta().getIdReceta());
                    rmDTO.setIdMedicamento(RM.getMedicamento().getIdMedicamento());
                    rmDTO.setIndicaciones(RM.getIndicaciones());
                    return rmDTO;
                }).collect(Collectors.toList());
                rDTO.setRecetaMedicamentos(meds);
                return rDTO;
            }).collect(Collectors.toList());
            dto.setRecetas(recetas);

            return dto;
        }).collect(Collectors.toList());
    }
    @Override
    public PacienteDTO obtenerPaciente(Integer id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        return modelMapper.map(paciente, PacienteDTO.class);
    }
}

