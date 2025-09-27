package com.upc.g1tf.services;

import com.upc.g1tf.dtos.ProfesionalSaludDTO;
import com.upc.g1tf.entities.ProfesionalSalud;
import com.upc.g1tf.interfaces.IProfesionalSaludService;
import com.upc.g1tf.repositories.ProfesionalSaludRepository;
import jakarta.validation.ValidationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfesionalSaludService implements IProfesionalSaludService {
    @Autowired
    private ProfesionalSaludRepository profesionalSaludRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ProfesionalSaludDTO registrarProfesional(ProfesionalSaludDTO profesionalSaludDTO) {
        if (profesionalSaludDTO.getNombres() == null || profesionalSaludDTO.getApellidos() == null ||
                profesionalSaludDTO.getEspecialidad() == null || profesionalSaludDTO.getEmail() == null ||
                profesionalSaludDTO.getTelefono() == null) {
            throw new ValidationException("Todos los campos obligatorios deben estar completos.");
        }

        // Validar duplicidad de colegiatura
        if (profesionalSaludRepository.findByColegiatura(profesionalSaludDTO.getColegiatura()).isPresent()) {
            throw new ValidationException("Colegiatura ya registrada.");
        }

        // Validar duplicidad de correos (de profesionales de salud debe ser único)
        if (profesionalSaludRepository.findByEmail(profesionalSaludDTO.getEmail()).isPresent()) {
            throw new ValidationException("El email ya está registrado.");
        }

        // Convertir DTO a Entidad
        ProfesionalSalud profesionalSalud = modelMapper.map(profesionalSaludDTO, ProfesionalSalud.class);

        // Guardar en BD
        profesionalSalud.setIdProfesional(null);
        ProfesionalSalud nuevoProfesional = profesionalSaludRepository.save(profesionalSalud);

        // Convertir Entidad a DTO
        return modelMapper.map(nuevoProfesional, ProfesionalSaludDTO.class);
    }


}
