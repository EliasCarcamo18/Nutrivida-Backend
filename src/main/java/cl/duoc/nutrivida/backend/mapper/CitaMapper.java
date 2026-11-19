package cl.duoc.nutrivida.backend.mapper;

import cl.duoc.nutrivida.backend.dto.CitaRequestDTO;
import cl.duoc.nutrivida.backend.dto.CitaResponseDTO;
import cl.duoc.nutrivida.backend.model.Cita;
import org.springframework.stereotype.Component;

@Component
public class CitaMapper {

    public Cita toEntity(CitaRequestDTO dto) {
        Cita cita = new Cita();
        cita.setNombrePaciente(dto.nombrePaciente());
        cita.setEmail(dto.email());
        cita.setTelefono(dto.telefono());
        cita.setFechaCita(dto.fechaCita());
        cita.setServicio(dto.servicio());
        cita.setEstado("PENDIENTE");
        return cita;
    }

    public CitaResponseDTO toResponseDTO(Cita cita) {
        return new CitaResponseDTO(
            cita.getId(),
            cita.getNombrePaciente(),
            cita.getEmail(),
            cita.getTelefono(),
            cita.getFechaCita(),
            cita.getServicio(),
            cita.getEstado()
        );
    }
}