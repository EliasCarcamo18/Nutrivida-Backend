package cl.duoc.nutrivida.backend.repository;

import cl.duoc.nutrivida.backend.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {
}