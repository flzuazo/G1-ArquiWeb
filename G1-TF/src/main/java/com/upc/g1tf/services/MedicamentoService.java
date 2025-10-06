package com.upc.g1tf.services;

import com.upc.g1tf.dtos.MedicamentoDTO;
import com.upc.g1tf.entities.Medicamento;
import com.upc.g1tf.interfaces.IMedicamentoService;
import com.upc.g1tf.repositories.MedicamentoRepository;
import jakarta.validation.ValidationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicamentoService implements IMedicamentoService {

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MedicamentoDTO insertarMedicamento(MedicamentoDTO medicamentoDTO) {
        // Criterio: Nombre y dosis son obligatorios
        if (medicamentoDTO.getNombre() == null || medicamentoDTO.getNombre().isBlank() ||
                medicamentoDTO.getDosis() == null || medicamentoDTO.getDosis().isBlank()) {
            throw new ValidationException("El nombre y la dosis del medicamento son obligatorios.");
        }

        // Criterio: Validar que no exista un medicamento con el mismo nombre y dosis
        medicamentoRepository.findByNombreAndDosis(medicamentoDTO.getNombre(), medicamentoDTO.getDosis())
                .ifPresent(m -> {
                    throw new ValidationException("Ya existe un medicamento con el nombre '" + m.getNombre() + "' y la dosis '" + m.getDosis() + "'.");
                });

        Medicamento medicamento = modelMapper.map(medicamentoDTO, Medicamento.class);
        Medicamento medicamentoGuardado = medicamentoRepository.save(medicamento);
        return modelMapper.map(medicamentoGuardado, MedicamentoDTO.class);
    }
    /**
     * HU15: Devuelve una lista de todos los medicamentos del catálogo.
     * Utiliza el metodo findAll() del repositorio y mapea cada entidad a su DTO.
     */
    @Override
    public List<MedicamentoDTO> listarMedicamentos() {
        return medicamentoRepository.findAll().stream()
                .map(medicamento -> modelMapper.map(medicamento, MedicamentoDTO.class))
                .collect(Collectors.toList());
    }

    // --- MÉTODOS RESTANTES (SIN IMPLEMENTAR) ---
    @Override
    public MedicamentoDTO buscarMedicamentoPorId(Long id) { return null; }

    @Override
    public MedicamentoDTO modificarMedicamento(MedicamentoDTO medicamentoDTO) { return null; }

    @Override
    public void eliminarMedicamento(Long id) { }

}