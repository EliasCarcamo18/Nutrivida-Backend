package cl.duoc.nutrivida.backend.controller;

import cl.duoc.nutrivida.backend.dto.ContactoRequestDTO;
import cl.duoc.nutrivida.backend.model.Contacto;
import cl.duoc.nutrivida.backend.repository.ContactoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/v1/contacto", "/api/contacto"})
@Tag(name = "Contacto", description = "Mensajes del formulario de contacto")
public class ContactoController {

    private final ContactoRepository contactoRepository;

    public ContactoController(ContactoRepository contactoRepository) {
        this.contactoRepository = contactoRepository;
    }

    @PostMapping
    @Operation(summary = "Enviar mensaje de contacto (Público)")
    public ResponseEntity<Contacto> enviar(@Valid @RequestBody ContactoRequestDTO request) {
        Contacto c = new Contacto();
        c.setNombre(request.nombre());
        c.setEmail(request.email());
        c.setAsunto(request.asunto());
        c.setMensaje(request.mensaje());
        return ResponseEntity.status(HttpStatus.CREATED).body(contactoRepository.save(c));
    }

    @GetMapping
    @Operation(summary = "Listar mensajes recibidos (Requiere Admin)")
    public ResponseEntity<List<Contacto>> listar() {
        return ResponseEntity.ok(contactoRepository.findAll());
    }
}