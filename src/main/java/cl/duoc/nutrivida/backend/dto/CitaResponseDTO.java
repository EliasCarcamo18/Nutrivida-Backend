package cl.duoc.nutrivida.backend.dto;

public record CitaResponseDTO(
    Long id,
    String nombrePaciente,
    String email,
    String telefono,
    String fechaCita,
    String servicio,
    String estado
) {}