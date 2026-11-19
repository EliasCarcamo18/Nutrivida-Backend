package cl.duoc.nutrivida.backend.repository;

import cl.duoc.nutrivida.backend.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    boolean existsByServicioAndFechaCita(String servicio, String fechaCita);
    List<Cita> findByServicioAndFechaCitaStartingWith(String servicio, String fechaPrefix);
}