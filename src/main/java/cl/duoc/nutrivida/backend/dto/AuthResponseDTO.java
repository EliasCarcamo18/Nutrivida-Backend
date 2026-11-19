package cl.duoc.nutrivida.backend.dto;

public record AuthResponseDTO(
    String token,
    String email,
    String nombre,
    String role
) {}