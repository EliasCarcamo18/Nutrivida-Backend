package cl.duoc.nutrivida.backend.controller;

import cl.duoc.nutrivida.backend.dto.UsuarioResponseDTO;
import cl.duoc.nutrivida.backend.exception.ResourceNotFoundException;
import cl.duoc.nutrivida.backend.model.Usuario;
import cl.duoc.nutrivida.backend.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios (Admin)", description = "Gestión de usuarios, roles y estados (Requiere rol ADMINISTRADOR)")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    @Operation(summary = "Listar todos los usuarios registrados")
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        return ResponseEntity.ok(usuarioRepository.findAll().stream().map(UsuarioResponseDTO::fromEntity).toList());
    }

    @PatchMapping("/{id}/rol")
    @Operation(summary = "Actualizar el rol de un usuario")
    public ResponseEntity<UsuarioResponseDTO> actualizarRol(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        String nuevoRol = body.get("rol");
        if (nuevoRol != null && List.of("PACIENTE", "NUTRICIONISTA", "ADMINISTRADOR").contains(nuevoRol)) {
            u.setRol(nuevoRol);
            usuarioRepository.save(u);
        }
        return ResponseEntity.ok(UsuarioResponseDTO.fromEntity(u));
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Activar o desactivar cuenta de usuario")
    public ResponseEntity<UsuarioResponseDTO> cambiarEstado(@PathVariable Long id, @RequestBody Map<String, Boolean> body) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        if (body.containsKey("activo")) {
            u.setActivo(Boolean.TRUE.equals(body.get("activo")));
            usuarioRepository.save(u);
        }
        return ResponseEntity.ok(UsuarioResponseDTO.fromEntity(u));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario por ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}