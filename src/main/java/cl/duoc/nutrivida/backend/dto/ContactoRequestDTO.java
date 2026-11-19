package cl.duoc.nutrivida.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ContactoRequestDTO(
    @NotBlank(message = "El nombre es obligatorio")
    String nombre,

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Correo inválido")
    String email,

    String asunto,

    @NotBlank(message = "El mensaje es obligatorio")
    String mensaje
) {}