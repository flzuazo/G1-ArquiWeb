package com.upc.g1tf.services;

import com.upc.g1tf.dtos.PacienteAtendidoDTO;
import com.upc.g1tf.dtos.ProfesionalSaludDTO;
import com.upc.g1tf.entities.ProfesionalSalud;
import com.upc.g1tf.interfaces.IProfesionalSaludService;
import com.upc.g1tf.repositories.ConsultaRepository;
import com.upc.g1tf.repositories.ProfesionalSaludRepository;
import com.upc.g1tf.security.entities.Role;
import com.upc.g1tf.security.entities.User;
import com.upc.g1tf.security.repositories.RoleRepository;
import com.upc.g1tf.security.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProfesionalSaludService implements IProfesionalSaludService {
    @Autowired
    private ProfesionalSaludRepository profesionalSaludRepository;
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;


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
        profesionalSalud.setIdProfesional(null);
        ProfesionalSalud nuevoProfesional = profesionalSaludRepository.save(profesionalSalud);

        User user = new User();
        user.setUsername(profesionalSaludDTO.getEmail());
        user.setPassword(passwordEncoder.encode("123456"));

        // Rol PROFESIONAL
        Role rolPro = roleRepository.findByName("ROLE_PROFESIONALSALUD");
        user.setRoles(Set.of(rolPro));

        user.setProfesionalSalud(nuevoProfesional);

        userService.save(user);

        // Convertir Entidad a DTO
        return modelMapper.map(nuevoProfesional, ProfesionalSaludDTO.class);
    }

    @Override
    public List<PacienteAtendidoDTO> listarPacientesAtendidos(Integer idProfesional) {
        List<PacienteAtendidoDTO> pacientes = consultaRepository.findPacientesAtendidos(idProfesional);
        if (pacientes.isEmpty()) {
            throw new EntityNotFoundException("El doctor no tiene consultas registradas.");
        }
        return pacientes;
    }

    @Override
    public List<ProfesionalSaludDTO> listar() {
        return profesionalSaludRepository.findAll().stream()
                .map(p -> modelMapper.map(p, ProfesionalSaludDTO.class))
                .collect(Collectors.toList());
    }


}
