package cl.duoc.nutrivida.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
    @NotBlank(message = "El nombre es obligatorio") String nombre,
    @NotBlank(message = "El correo es obligatorio") @Email(message = "Formato de correo inválido") String email,
    @NotBlank(message = "La contraseña es obligatoria") @Size(min = 6, message = "Mínimo 6 caracteres") String password
) {}