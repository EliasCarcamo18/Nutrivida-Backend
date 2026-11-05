package cl.duoc.nutrivida.backend.mapper;

import cl.duoc.nutrivida.backend.dto.CitaRequestDTO;
import cl.duoc.nutrivida.backend.dto.CitaResponseDTO;
import cl.duoc.nutrivida.backend.model.Cita;

public class CitaMapper {

    public static Cita toEntity(CitaRequestDTO dto) {
        Cita cita = new Cita();
        cita.setNombrePaciente(dto.nombrePaciente());
        cita.setEmail(dto.email());
        cita.setTelefono(dto.telefono());
        cita.setFechaCita(dto.fechaCita());
        cita.setServicio(dto.servicio());
        cita.setEstado("PENDIENTE");
        return cita;
    }

    public static CitaResponseDTO toDTO(Cita entity) {
        return new CitaResponseDTO(
            entity.getId(),
            entity.getNombrePaciente(),
            entity.getEmail(),
            entity.getTelefono(),
            entity.getFechaCita(),
            entity.getServicio(),
            entity.getEstado()
        );
    }
}