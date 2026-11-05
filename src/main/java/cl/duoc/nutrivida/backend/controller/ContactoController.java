package cl.duoc.nutrivida.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/contacto")
public class ContactoController {

    @PostMapping
    public ResponseEntity<Map<String, String>> recibirMensajeContacto(@RequestBody Map<String, String> contactoData) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("status", "exito");
        respuesta.put("mensaje", "Mensaje de contacto recibido correctamente");

        return ResponseEntity.ok(respuesta);
    }
}