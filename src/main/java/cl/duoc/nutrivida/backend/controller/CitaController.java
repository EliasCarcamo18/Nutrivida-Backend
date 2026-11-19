package cl.duoc.nutrivida.backend.controller;

import cl.duoc.nutrivida.backend.dto.CitaRequestDTO;
import cl.duoc.nutrivida.backend.dto.CitaResponseDTO;
import cl.duoc.nutrivida.backend.service.CitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/v1/citas", "/api/citas"})
@Tag(name = "Citas", description = "Gestión y disponibilidad de citas nutricionales")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping("/ocupadas")
    @Operation(summary = "Consultar horas ocupadas (Público)", description = "Devuelve los bloques horarios ya reservados para un especialista y fecha sin exponer datos personales")
    public ResponseEntity<List<String>> listarHorasOcupadas(
            @RequestParam String servicio,
            @RequestParam String fecha) {
        return ResponseEntity.ok(citaService.obtenerHorasOcupadas(servicio, fecha));
    }

    @GetMapping
    @Operation(summary = "Listar todas las citas (Requiere Admin)")
    public ResponseEntity<List<CitaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(citaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cita por ID (Requiere Admin)")
    public ResponseEntity<CitaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Agendar nueva cita (Público)")
    public ResponseEntity<CitaResponseDTO> crear(@Valid @RequestBody CitaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(citaService.crearCita(request));
    }

    @PatchMapping("/{id}/finalizar")
    @Operation(summary = "Marcar cita como finalizada (Requiere Admin)")
    public ResponseEntity<CitaResponseDTO> finalizar(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.cambiarEstado(id, "FINALIZADA"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cita (Requiere Admin)")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        citaService.eliminarCita(id);
        return ResponseEntity.noContent().build();
    }
}