package cl.duoc.nutrivida.backend;

import cl.duoc.nutrivida.backend.dto.CitaRequestDTO;
import cl.duoc.nutrivida.backend.dto.CitaResponseDTO;
import cl.duoc.nutrivida.backend.exception.HorarioOcupadoException;
import cl.duoc.nutrivida.backend.service.CitaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:nutrivida_test;MODE=MySQL;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.flyway.enabled=true",
    "spring.jpa.hibernate.ddl-auto=validate"
})
@Transactional
class CitaPersistenciaH2Test {

    @Autowired
    private CitaService citaService;

    @Test
    void deberiaInsertarCitaYRecuperarIdAutogenerado() {
        CitaRequestDTO req = new CitaRequestDTO(
            "Camila Rojas",
            "camila@ejemplo.cl",
            "+56912345678",
            "2026-12-10 09:00 hrs",
            "Nut. Carolina Fuentes M. (Obesidad y síndrome metabólico)"
        );

        CitaResponseDTO creada = citaService.crearCita(req);

        assertNotNull(creada.id());
        assertEquals("PENDIENTE", creada.estado());
    }

    @Test
    void deberiaBloquearDobleReservaEnMismoEspecialistaYHorario() {
        CitaRequestDTO req = new CitaRequestDTO(
            "Paciente Uno",
            "uno@ejemplo.cl",
            "+56911111111",
            "2026-12-11 10:30 hrs",
            "Nut. Carolina Fuentes M. (Obesidad y síndrome metabólico)"
        );

        citaService.crearCita(req);

        CitaRequestDTO duplicada = new CitaRequestDTO(
            "Paciente Dos",
            "dos@ejemplo.cl",
            "+56922222222",
            "2026-12-11 10:30 hrs",
            "Nut. Carolina Fuentes M. (Obesidad y síndrome metabólico)"
        );

        assertThrows(HorarioOcupadoException.class, () -> citaService.crearCita(duplicada));
    }

    @Test
    void deberiaListarHorasOcupadasPorEspecialistaYFecha() {
        String especialista = "Nut. Felipe Arriagada G. (Nutrición Deportiva)";
        citaService.crearCita(new CitaRequestDTO("Luis", "luis@cl.cl", "+56933333333", "2026-12-15 11:00 hrs", especialista));

        List<String> ocupadas = citaService.obtenerHorasOcupadas(especialista, "2026-12-15");

        assertTrue(ocupadas.contains("11:00 hrs"));
    }
}