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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

        // === Actualizar alergias (reemplazar, no concatenar) ===
        if (pacientehistorialDTO.getAlergias() != null && !pacientehistorialDTO.getAlergias().isBlank()) {
            String nuevo = pacientehistorialDTO.getAlergias().trim();
            // Se reemplaza el valor actual de alergias por el nuevo
            paciente.setAlergias(nuevo.length() > 500 ? nuevo.substring(0, 500) : nuevo); // Limitar a 500 caracteres
        } else {
            // Si el campo alergias está vacío, se lo puede dejar en null si deseas que se borre
            paciente.setAlergias(null);
        }

        // === Actualizar antecedentes (reemplazar, no concatenar) ===
        if (pacientehistorialDTO.getAntecedentes() != null && !pacientehistorialDTO.getAntecedentes().isBlank()) {
            String nuevo = pacientehistorialDTO.getAntecedentes().trim();
            // Se reemplaza el valor actual de antecedentes por el nuevo
            paciente.setAntecedentes(nuevo);
        } else {
            // Si el campo any está vacío, se lo puede dejar en null si deseas que se borre
            paciente.setAntecedentes(null);
        }

        // Actualizar la fecha de la última actualización con la fecha y hora actual
        pacientehistorialDTO.setFechaUltimaActualizacion(LocalDateTime.now()); // Setear la fecha actual
        paciente.setFechaUltimaActualizacion(pacientehistorialDTO.getFechaUltimaActualizacion()); // Guardar en paciente

        // Guardamos el paciente con los nuevos datos

        Paciente actualizado = pacienteRepository.save(paciente);
        return modelMapper.map(actualizado, PacienteDTO.class);
    }

    @Override
    // Eliminar un registro del historial
    public void eliminarHistorial(Integer registroId) {
        // Buscar el paciente usando el registroId
        Paciente paciente = pacienteRepository.findById(registroId)
                .orElseThrow(() -> new EntityNotFoundException("Registro no encontrado con ID: " + registroId));

        // Eliminar solo las alergias y antecedentes del paciente
        paciente.setAlergias(null);  // Limpiar las alergias
        paciente.setAntecedentes(null);  // Limpiar los antecedentes

        paciente.setFechaUltimaActualizacion(LocalDateTime.now());

        // Eliminar el paciente
        pacienteRepository.save(paciente);
    }

    @Override
    // Obtener todos los registros del historial de un paciente
    public List<PacienteHistorialDTO> obtenerHistorial(Integer idPaciente) {
        // Buscar los registros del paciente
        List<Paciente> registros = pacienteRepository.findByIdPaciente(idPaciente);

        // Convertir los registros de la entidad a DTO
        return registros.stream()
                .map(paciente -> new PacienteHistorialDTO(
                        paciente.getIdPaciente(),
                        paciente.getAlergias(),
                        paciente.getAntecedentes(),
                        paciente.getFechaUltimaActualizacion()
                ))
                .collect(Collectors.toList());
    }


    public boolean validarPaciente(Integer id) {
        return pacienteRepository.findById(id).isPresent(); // Retorna true si el paciente existe, false si no
    }


}
