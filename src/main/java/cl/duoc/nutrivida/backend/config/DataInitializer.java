package cl.duoc.nutrivida.backend.config;

import cl.duoc.nutrivida.backend.model.Usuario;
import cl.duoc.nutrivida.backend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsuarios(UsuarioRepository repo, PasswordEncoder encoder) {
        return args -> {
            crearSiNoExiste(repo, encoder, "Administrador NutriVida", "admin@nutrivida.cl", "Admin1234!", "ADMINISTRADOR");
            crearSiNoExiste(repo, encoder, "Nut. Carolina Fuentes", "nutricionista@nutrivida.cl", "Nutri1234!", "NUTRICIONISTA");
            crearSiNoExiste(repo, encoder, "Paciente NutriVida", "paciente@nutrivida.cl", "Paciente1234!", "PACIENTE");
        };
    }

    private void crearSiNoExiste(UsuarioRepository repo, PasswordEncoder encoder, String nombre, String email, String rawPass, String rol) {
        if (!repo.existsByEmail(email)) {
            Usuario u = new Usuario();
            u.setNombre(nombre);
            u.setEmail(email);
            u.setPassword(encoder.encode(rawPass));
            u.setRol(rol);
            u.setActivo(true);
            repo.save(u);
        }
    }
}