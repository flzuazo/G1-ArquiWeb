package com.upc.g1tf.services;

import com.upc.g1tf.dtos.ConsultaDTO;
import com.upc.g1tf.dtos.DiagnosticoDTO;
import com.upc.g1tf.dtos.RecetaDTO;
import com.upc.g1tf.dtos.RecetaMedicamentoDTO;
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

    @Autowired
    private MedicamentoRepository  medicamentoRepository;

    // ====== Mapper DTO <-> Entity ======
    private ConsultaDTO toDTO(Consulta consulta) {
        ConsultaDTO dto = new ConsultaDTO();
        dto.setIdConsulta(consulta.getIdConsulta());
        dto.setIdPaciente(consulta.getPaciente().getIdPaciente());
        dto.setIdProfesional(consulta.getProfesional().getIdProfesional());
        dto.setIdCentroMedico(consulta.getCentroMedico().getIdCentroMedico());
        dto.setFechaConsulta(consulta.getFechaConsulta());

        // === Diagnósticos ===
        if (consulta.getDiagnosticos() != null) {
            List<DiagnosticoDTO> diagnosticosDTO = consulta.getDiagnosticos().stream().map(d -> {
                DiagnosticoDTO ddto = new DiagnosticoDTO();
                ddto.setIdDiagnostico(d.getIdDiagnostico());
                ddto.setDescripcion(d.getDescripcion());
                ddto.setCodigoCIE10(d.getCodigoCIE10());
                // 👇 Agregamos el idConsulta
                ddto.setIdConsulta(consulta.getIdConsulta());
                return ddto;
            }).collect(Collectors.toList());
            dto.setDiagnosticos(diagnosticosDTO);
        }

        // === Recetas ===
        if (consulta.getRecetas() != null) {
            List<RecetaDTO> recetasDTO = consulta.getRecetas().stream().map(r -> {
                RecetaDTO rdto = new RecetaDTO();
                rdto.setIdReceta(r.getIdReceta());
                rdto.setFechaEmision(r.getFechaEmision());
                // 👇 Agregamos el idConsulta
                rdto.setIdConsulta(consulta.getIdConsulta());

                // === RecetaMedicamentos ===
                if (r.getRecetaMedicamentos() != null) {
                    List<RecetaMedicamentoDTO> rmDTOs = r.getRecetaMedicamentos().stream().map(rm -> {
                        RecetaMedicamentoDTO rmdto = new RecetaMedicamentoDTO();
                        rmdto.setIdMedicamento(rm.getMedicamento().getIdMedicamento());
                        rmdto.setIndicaciones(rm.getIndicaciones());
                        // 👇 Agregamos el idReceta
                        rmdto.setIdReceta(r.getIdReceta());
                        return rmdto;
                    }).collect(Collectors.toList());
                    rdto.setRecetaMedicamentos(rmDTOs);
                }

                return rdto;
            }).collect(Collectors.toList());
            dto.setRecetas(recetasDTO);
        }

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

        // Copia final para lambdas
        final Consulta consultaFinal = consulta;

        // === Diagnósticos ===
        if (dto.getDiagnosticos() != null && !dto.getDiagnosticos().isEmpty()) {
            List<Diagnostico> diagnosticos = dto.getDiagnosticos().stream().map(d -> {
                Diagnostico diag = new Diagnostico();
                diag.setDescripcion(d.getDescripcion());
                diag.setCodigoCIE10(d.getCodigoCIE10());
                diag.setConsulta(consultaFinal); // ✅ usa la variable final
                return diag;
            }).collect(Collectors.toList());

            consulta.setDiagnosticos(diagnosticos);
        }

        // === Recetas ===
        if (dto.getRecetas() != null && !dto.getRecetas().isEmpty()) {
            List<Receta> recetas = dto.getRecetas().stream().map(r -> {
                Receta receta = new Receta();
                receta.setFechaEmision(r.getFechaEmision());
                receta.setConsulta(consultaFinal); // ✅ usa la variable final

                // RecetaMedicamentos dentro de la receta
                if (r.getRecetaMedicamentos() != null && !r.getRecetaMedicamentos().isEmpty()) {
                    List<RecetaMedicamento> listaRecetaMed = r.getRecetaMedicamentos().stream().map(rmDto -> {
                        RecetaMedicamento rm = new RecetaMedicamento();

                        Medicamento med = medicamentoRepository.findById(rmDto.getIdMedicamento())
                                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado: " + rmDto.getIdMedicamento()));

                        rm.setMedicamento(med);
                        rm.setReceta(receta);
                        rm.setIndicaciones(rmDto.getIndicaciones());

                        // 🔧 Inicializar clave compuesta (idReceta, idMedicamento)
                        RecetaMedicamentoId id = new RecetaMedicamentoId();
                        id.setIdMedicamento(med.getIdMedicamento());
                        // el idReceta aún no existe porque la receta no se ha guardado,
                        // pero Hibernate lo completará automáticamente por el @MapsId.
                        rm.setId(id);

                        return rm;
                    }).collect(Collectors.toList());

                    receta.setRecetaMedicamentos(listaRecetaMed);
                }

                return receta;
            }).collect(Collectors.toList());

            consulta.setRecetas(recetas);
        }

        // === Guardar consulta (con cascade guarda diagnósticos y recetas) ===
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
