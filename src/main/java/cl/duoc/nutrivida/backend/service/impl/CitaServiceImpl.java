package cl.duoc.nutrivida.backend.service.impl;

import cl.duoc.nutrivida.backend.dto.CitaRequestDTO;
import cl.duoc.nutrivida.backend.dto.CitaResponseDTO;
import cl.duoc.nutrivida.backend.exception.HorarioOcupadoException;
import cl.duoc.nutrivida.backend.exception.ResourceNotFoundException;
import cl.duoc.nutrivida.backend.mapper.CitaMapper;
import cl.duoc.nutrivida.backend.model.Cita;
import cl.duoc.nutrivida.backend.repository.CitaRepository;
import cl.duoc.nutrivida.backend.service.CitaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;
    private final CitaMapper citaMapper;

    public CitaServiceImpl(CitaRepository citaRepository, CitaMapper citaMapper) {
        this.citaRepository = citaRepository;
        this.citaMapper = citaMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaResponseDTO> obtenerTodas() {
        return citaRepository.findAll().stream()
                .map(citaMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CitaResponseDTO obtenerPorId(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con id: " + id));
        return citaMapper.toResponseDTO(cita);
    }

    @Override
    @Transactional
    public CitaResponseDTO crearCita(CitaRequestDTO request) {
        if (citaRepository.existsByServicioAndFechaCita(request.servicio(), request.fechaCita())) {
            throw new HorarioOcupadoException("El horario " + request.fechaCita() + " ya está ocupado para este especialista.");
        }
        Cita cita = citaMapper.toEntity(request);
        Cita guardada = citaRepository.save(cita);
        return citaMapper.toResponseDTO(guardada);
    }

    @Override
    @Transactional
    public CitaResponseDTO cambiarEstado(Long id, String nuevoEstado) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con id: " + id));
        cita.setEstado(nuevoEstado);
        return citaMapper.toResponseDTO(citaRepository.save(cita));
    }

    @Override
    @Transactional
    public void eliminarCita(Long id) {
        if (!citaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cita no encontrada con id: " + id);
        }
        citaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> obtenerHorasOcupadas(String servicio, String fecha) {
        return citaRepository.findByServicioAndFechaCitaStartingWith(servicio, fecha).stream()
                .filter(c -> !"CANCELADA".equalsIgnoreCase(c.getEstado()))
                .map(Cita::getFechaCita)
                .map(fc -> fc.length() > fecha.length() ? fc.substring(fecha.length()).trim() : fc)
                .toList();
    }
}