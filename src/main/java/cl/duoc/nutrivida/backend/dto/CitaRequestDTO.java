package cl.duoc.nutrivida.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CitaRequestDTO(
    @NotBlank(message = "El nombre del paciente es obligatorio")
    String nombrePaciente,

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un email valido")
    String email,

    @NotBlank(message = "El telefono es obligatorio")
    String telefono,

    @NotBlank(message = "La fecha es obligatoria")
    String fechaCita,

    @NotBlank(message = "El servicio es obligatorio")
    String servicio
) {}