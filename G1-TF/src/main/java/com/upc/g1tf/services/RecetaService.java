package com.upc.g1tf.services;
import com.upc.g1tf.dtos.RecetaDTO;
import com.upc.g1tf.dtos.RecetaMedicamentoDTO;
import com.upc.g1tf.entities.Consulta;
import com.upc.g1tf.entities.Medicamento;
import com.upc.g1tf.entities.Receta;
import com.upc.g1tf.entities.RecetaMedicamento;
import com.upc.g1tf.entities.RecetaMedicamentoId;
import com.upc.g1tf.interfaces.IRecetaService;
import com.upc.g1tf.repositories.ConsultaRepository;
import com.upc.g1tf.repositories.MedicamentoRepository;
import com.upc.g1tf.repositories.RecetaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecetaService implements IRecetaService {

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Autowired
    private ConsultaRepository consultaRepository;


    private RecetaDTO toDTO(Receta receta) {
        RecetaDTO dto = new RecetaDTO();
        dto.setIdReceta(receta.getIdReceta());
        dto.setIdConsulta(receta.getConsulta().getIdConsulta());
        dto.setFechaEmision(receta.getFechaEmision());

        List<RecetaMedicamentoDTO> medicamentos = receta.getRecetaMedicamentos() != null
                ? receta.getRecetaMedicamentos().stream()
                .map(rm -> {
                    RecetaMedicamentoDTO mDto = new RecetaMedicamentoDTO();
                    mDto.setIdReceta(rm.getReceta().getIdReceta());
                    mDto.setIdMedicamento(rm.getMedicamento().getIdMedicamento());
                    mDto.setIndicaciones(rm.getIndicaciones());
                    return mDto;
                })
                .collect(Collectors.toList())
                : new ArrayList<>();

        dto.setRecetaMedicamentos(medicamentos);
        return dto;
    }
    private Receta toEntity(RecetaDTO dto) {
        Receta receta = new Receta();
        receta.setFechaEmision(dto.getFechaEmision() != null ? dto.getFechaEmision() : LocalDate.now());

        if (dto.getIdConsulta() != null) {
            Consulta consulta = new Consulta();
            consulta.setIdConsulta(dto.getIdConsulta());
            receta.setConsulta(consulta);
        }


        receta.setRecetaMedicamentos(new ArrayList<>());

        return receta;
    }


    @Transactional
    @Override
    public RecetaDTO insertarReceta(RecetaDTO recetaDTO) {

        Receta receta = new Receta();
        receta.setFechaEmision(recetaDTO.getFechaEmision() != null ? recetaDTO.getFechaEmision() : LocalDate.now());

        if (recetaDTO.getIdConsulta() != null) {
            Consulta consulta = consultaRepository.findById(recetaDTO.getIdConsulta())
                    .orElseThrow(() -> new RuntimeException("Consulta no encontrada: " + recetaDTO.getIdConsulta()));
            receta.setConsulta(consulta);
        }


        receta.setRecetaMedicamentos(new ArrayList<>());


        receta = recetaRepository.save(receta);


        if (recetaDTO.getRecetaMedicamentos() != null) {
            for (RecetaMedicamentoDTO rmDto : recetaDTO.getRecetaMedicamentos()) {
                Medicamento medicamento = medicamentoRepository.findById(rmDto.getIdMedicamento())
                        .orElseThrow(() -> new RuntimeException("Medicamento no encontrado: " + rmDto.getIdMedicamento()));

                RecetaMedicamento rm = new RecetaMedicamento();


                rm.setReceta(receta);
                rm.setMedicamento(medicamento);
                rm.setIndicaciones(rmDto.getIndicaciones());


                RecetaMedicamentoId id = new RecetaMedicamentoId();
                id.setIdReceta(receta.getIdReceta());
                id.setIdMedicamento(medicamento.getIdMedicamento());
                rm.setId(id);

                receta.getRecetaMedicamentos().add(rm);
            }


            receta = recetaRepository.save(receta);
        }

        return toDTO(receta);
    }



    @Transactional
    @Override
    public RecetaDTO modificarReceta(RecetaDTO recetaDTO) {
        Receta receta = recetaRepository.findById(recetaDTO.getIdReceta())
                .orElseThrow(() -> new RuntimeException("Receta no encontrada: " + recetaDTO.getIdReceta()));

        receta.setFechaEmision(recetaDTO.getFechaEmision() != null ? recetaDTO.getFechaEmision() : receta.getFechaEmision());


        receta.getRecetaMedicamentos().clear();


        List<RecetaMedicamento> listaRM = new ArrayList<>();
        if (recetaDTO.getRecetaMedicamentos() != null) {
            for (RecetaMedicamentoDTO rmDto : recetaDTO.getRecetaMedicamentos()) {
                Medicamento medicamento = medicamentoRepository.findById(rmDto.getIdMedicamento())
                        .orElseThrow(() -> new RuntimeException("Medicamento no encontrado: " + rmDto.getIdMedicamento()));

                RecetaMedicamento rm = new RecetaMedicamento();
                rm.setReceta(receta);
                rm.setMedicamento(medicamento);
                rm.setIndicaciones(rmDto.getIndicaciones());

                RecetaMedicamentoId id = new RecetaMedicamentoId();
                id.setIdReceta(receta.getIdReceta());
                id.setIdMedicamento(medicamento.getIdMedicamento());
                rm.setId(id);

                listaRM.add(rm);
            }
        }
        receta.setRecetaMedicamentos(listaRM);

        receta = recetaRepository.save(receta);
        return toDTO(receta);
    }

    @Override
    public List<RecetaDTO> listarRecetas() {
        return recetaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RecetaDTO buscarRecetaPorId(Integer id) {
        Receta receta = recetaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada: " + id));
        return toDTO(receta);
    }

    @Transactional
    @Override
    public void eliminarReceta(Integer id) {
        if (recetaRepository.existsById(id)) {
            recetaRepository.deleteById(id);
        } else {
            throw new RuntimeException("No existe la receta con id: " + id);
        }
    }
}
