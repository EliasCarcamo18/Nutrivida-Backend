package cl.duoc.nutrivida.backend.service.impl;

import cl.duoc.nutrivida.backend.dto.CitaRequestDTO;
import cl.duoc.nutrivida.backend.dto.CitaResponseDTO;
import cl.duoc.nutrivida.backend.exception.ResourceNotFoundException;
import cl.duoc.nutrivida.backend.mapper.CitaMapper;
import cl.duoc.nutrivida.backend.model.Cita;
import cl.duoc.nutrivida.backend.repository.CitaRepository;
import cl.duoc.nutrivida.backend.service.CitaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;

    public CitaServiceImpl(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    @Override
    public List<CitaResponseDTO> obtenerTodas() {
        return citaRepository.findAll()
                .stream()
                .map(CitaMapper::toDTO)
                .toList();
    }

    @Override
    public CitaResponseDTO obtenerPorId(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con ID: " + id));
        return CitaMapper.toDTO(cita);
    }

    @Override
    public CitaResponseDTO crearCita(CitaRequestDTO request) {
        Cita nuevaCita = CitaMapper.toEntity(request);
        Cita guardada = citaRepository.save(nuevaCita);
        return CitaMapper.toDTO(guardada);
    }

    @Override
    public CitaResponseDTO cambiarEstado(Long id, String nuevoEstado) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con ID: " + id));
        cita.setEstado(nuevoEstado);
        Cita actualizada = citaRepository.save(cita);
        return CitaMapper.toDTO(actualizada);
    }

    @Override
    public void eliminarCita(Long id) {
        if (!citaRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar. Cita no encontrada con ID: " + id);
        }
        citaRepository.deleteById(id);
    }
}