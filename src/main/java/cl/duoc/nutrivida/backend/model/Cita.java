package cl.duoc.nutrivida.backend.model;

import jakarta.persistence.*;

@Entity
@Table(
    name = "citas",
    uniqueConstraints = @UniqueConstraint(name = "uk_servicio_fecha_cita", columnNames = {"servicio", "fecha_cita"})
)
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_paciente", nullable = false, length = 150)
    private String nombrePaciente;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false, length = 30)
    private String telefono;

    @Column(name = "fecha_cita", nullable = false, length = 50)
    private String fechaCita;

    @Column(nullable = false, length = 150)
    private String servicio;

    @Column(nullable = false, length = 30)
    private String estado = "PENDIENTE";

    public Cita() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombrePaciente() { return nombrePaciente; }
    public void setNombrePaciente(String nombrePaciente) { this.nombrePaciente = nombrePaciente; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getFechaCita() { return fechaCita; }
    public void setFechaCita(String fechaCita) { this.fechaCita = fechaCita; }

    public String getServicio() { return servicio; }
    public void setServicio(String servicio) { this.servicio = servicio; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}