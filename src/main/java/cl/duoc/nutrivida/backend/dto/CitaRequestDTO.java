package cl.duoc.nutrivida.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CitaRequestDTO(
    @NotBlank(message = "El nombre del paciente es obligatorio")
    String nombrePaciente,

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Formato de correo electrónico inválido")
    String email,

    @NotBlank(message = "El teléfono es obligatorio")
    String telefono,

    @NotBlank(message = "La fecha y hora de la cita son obligatorias")
    String fechaCita,

    @NotBlank(message = "El servicio o especialista es obligatorio")
    String servicio
) {}