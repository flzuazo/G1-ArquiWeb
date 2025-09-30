package com.upc.g1tf.services;

import com.upc.g1tf.dtos.CentroMedicoDTO;
import com.upc.g1tf.entities.CentroMedico;
import com.upc.g1tf.interfaces.ICentroMedicoService;
import com.upc.g1tf.repositories.CentroMedicoRepository;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CentroMedicoService implements ICentroMedicoService {

    @Autowired
    private CentroMedicoRepository centroMedicoRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CentroMedicoDTO registrarCentroMedico(CentroMedicoDTO centromedicoDTO) {
        if (centromedicoDTO.getNombreCentro() == null || centromedicoDTO.getNombreCentro().isBlank()
                || centromedicoDTO.getDireccion() == null || centromedicoDTO.getDireccion().isBlank()
                || centromedicoDTO.getTelefono() == null || centromedicoDTO.getTelefono().isBlank()) {
            throw new ValidationException("Nombre, dirección y teléfono son obligatorios.");
        }

        // Validación opcional de duplicado por nombre
        centroMedicoRepository.findByNombreCentro(centromedicoDTO.getNombreCentro())
                .ifPresent(c -> { throw new ValidationException("Ya existe un centro con ese nombre."); });

        CentroMedico entity = modelMapper.map(centromedicoDTO, CentroMedico.class);
        entity.setIdCentroMedico(null); // autogenerado

        CentroMedico guardado = centroMedicoRepository.save(entity);
        return modelMapper.map(guardado, CentroMedicoDTO.class);
    }
}
