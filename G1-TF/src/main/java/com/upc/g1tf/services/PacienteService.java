package com.upc.g1tf.services;

import com.upc.g1tf.dtos.PacienteDTO;
import com.upc.g1tf.dtos.PacienteHistorialDTO;
import com.upc.g1tf.dtos.PacienteUpdateDTO;
import com.upc.g1tf.entities.CentroMedico;
import com.upc.g1tf.entities.Consulta;
import com.upc.g1tf.entities.Paciente;
import com.upc.g1tf.entities.ProfesionalSalud;
import com.upc.g1tf.interfaces.IPacienteService;
import com.upc.g1tf.repositories.CentroMedicoRepository;
import com.upc.g1tf.repositories.PacienteRepository;
import com.upc.g1tf.repositories.ProfesionalSaludRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PacienteService implements IPacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CentroMedicoRepository centromedicoRepository;          // NEW
    @Autowired
    private ProfesionalSaludRepository profesionalsaludRepository;  // NEW


    // ===== HU02 – Registrar Paciente =====
    @Override
    public PacienteDTO registrarPaciente(PacienteDTO pacienteDTO) {
        if (pacienteDTO.getNombres() == null || pacienteDTO.getApellidos() == null ||
                pacienteDTO.getDni() == null || pacienteDTO.getFechaNacimiento() == null ||
                pacienteDTO.getSexo() == null) {
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
        Paciente actualizado = pacienteRepository.save(paciente);

        return modelMapper.map(actualizado, PacienteDTO.class);
    }

    // ===== HU12 – Actualizar historial (alergias + consultas) =====
    @Override
    public PacienteDTO actualizarHistorial(Integer idPaciente, PacienteHistorialDTO pacientehistorialDTO) {
        Paciente paciente = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado con ID: " + idPaciente));

        if (pacientehistorialDTO.getAlergias() != null) {
            paciente.setAlergias(pacientehistorialDTO.getAlergias());
        }

        if (pacientehistorialDTO.getConsultas() != null && !pacientehistorialDTO.getConsultas().isEmpty()) {
            if (paciente.getConsultas() == null) {
                paciente.setConsultas(new java.util.ArrayList<>());
            }

            pacientehistorialDTO.getConsultas().forEach(cDto -> {
                // validar obligatorios
                if (cDto.getIdCentroMedico() == null) {
                    throw new ValidationException("El campo idCentroMedico es obligatorio.");
                }
                if (cDto.getIdProfesional() == null) {
                    throw new ValidationException("El campo idProfesional es obligatorio.");
                }

                // validar existencia
                CentroMedico centro = centromedicoRepository.findById(cDto.getIdCentroMedico())
                        .orElseThrow(() -> new EntityNotFoundException("Centro médico no encontrado con ID: " + cDto.getIdCentroMedico()));

                ProfesionalSalud profesional = profesionalsaludRepository.findById(cDto.getIdProfesional())
                        .orElseThrow(() -> new EntityNotFoundException("Profesional no encontrado con ID: " + cDto.getIdProfesional()));

                // crear consulta
                Consulta consulta = new Consulta();
                consulta.setPaciente(paciente);
                consulta.setCentroMedico(centro);
                consulta.setProfesional(profesional);
                consulta.setFechaConsulta(cDto.getFechaConsulta());
                //consulta.setDiagnostico(cDto.getDiagnostico());
                //consulta.setTratamiento(cDto.getTratamiento());

                paciente.getConsultas().add(consulta);
            });
        }

        Paciente actualizado = pacienteRepository.save(paciente);
        return modelMapper.map(actualizado, PacienteDTO.class);
    }
}
