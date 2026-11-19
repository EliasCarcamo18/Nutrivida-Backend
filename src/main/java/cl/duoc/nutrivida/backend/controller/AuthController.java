package cl.duoc.nutrivida.backend.controller;

import cl.duoc.nutrivida.backend.dto.AuthResponseDTO;
import cl.duoc.nutrivida.backend.dto.LoginRequestDTO;
import cl.duoc.nutrivida.backend.dto.RegisterRequestDTO;
import cl.duoc.nutrivida.backend.model.Usuario;
import cl.duoc.nutrivida.backend.repository.UsuarioRepository;
import cl.duoc.nutrivida.backend.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticación JWT", description = "Login y registro de usuarios con emisión de token JWT")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión y obtener token JWT")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO request) {
        Usuario usuario = usuarioRepository.findByEmail(request.email()).orElse(null);
        if (usuario == null || !passwordEncoder.matches(request.password(), usuario.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("mensaje", "Credenciales inválidas. Verifica tu correo y contraseña."));
        }
        if (!usuario.isActivo()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("mensaje", "Tu cuenta se encuentra desactivada."));
        }
        String token = jwtService.generateToken(usuario);
        return ResponseEntity.ok(new AuthResponseDTO(token, usuario.getEmail(), usuario.getNombre(), usuario.getRol()));
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar nuevo paciente y obtener token JWT")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("mensaje", "Ya existe una cuenta registrada con este correo."));
        }
        Usuario nuevo = new Usuario();
        nuevo.setNombre(request.nombre());
        nuevo.setEmail(request.email());
        nuevo.setPassword(passwordEncoder.encode(request.password()));
        nuevo.setRol("PACIENTE");
        nuevo.setActivo(true);
        usuarioRepository.save(nuevo);

        String token = jwtService.generateToken(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponseDTO(token, nuevo.getEmail(), nuevo.getNombre(), nuevo.getRol()));
    }
}