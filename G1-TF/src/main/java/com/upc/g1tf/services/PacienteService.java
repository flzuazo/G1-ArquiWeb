package com.upc.g1tf.services;

import com.upc.g1tf.dtos.PacienteDTO;
import com.upc.g1tf.dtos.PacienteUpdateDTO;
import com.upc.g1tf.entities.Paciente;
import com.upc.g1tf.interfaces.IPacienteService;
import com.upc.g1tf.repositories.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PacienteService implements IPacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private ModelMapper modelMapper;

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
}
