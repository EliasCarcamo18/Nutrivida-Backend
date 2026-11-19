package cl.duoc.nutrivida.backend.dto;

import cl.duoc.nutrivida.backend.model.Usuario;

public record UsuarioResponseDTO(
    Long id,
    String nombre,
    String email,
    String rol,
    boolean activo
) {
    public static UsuarioResponseDTO fromEntity(Usuario u) {
        return new UsuarioResponseDTO(u.getId(), u.getNombre(), u.getEmail(), u.getRol(), u.isActivo());
    }
}