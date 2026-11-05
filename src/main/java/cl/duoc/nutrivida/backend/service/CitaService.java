package cl.duoc.nutrivida.backend.service;

import cl.duoc.nutrivida.backend.dto.CitaRequestDTO;
import cl.duoc.nutrivida.backend.dto.CitaResponseDTO;

import java.util.List;

public interface CitaService {
    List<CitaResponseDTO> obtenerTodas();
    CitaResponseDTO obtenerPorId(Long id);
    CitaResponseDTO crearCita(CitaRequestDTO request);
    CitaResponseDTO cambiarEstado(Long id, String nuevoEstado);
    void eliminarCita(Long id);
}