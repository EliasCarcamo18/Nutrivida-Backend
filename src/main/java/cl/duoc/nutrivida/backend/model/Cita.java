package cl.duoc.nutrivida.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "citas")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_paciente", nullable = false)
    private String nombrePaciente;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telefono;

    @Column(name = "fecha_cita", nullable = false)
    private String fechaCita;

    @Column(nullable = false)
    private String servicio;

    @Column(nullable = false)
    private String estado = "PENDIENTE";

    public Cita() {}

    public Cita(Long id, String nombrePaciente, String email, String telefono, String fechaCita, String servicio, String estado) {
        this.id = id;
        this.nombrePaciente = nombrePaciente;
        this.email = email;
        this.telefono = telefono;
        this.fechaCita = fechaCita;
        this.servicio = servicio;
        this.estado = estado;
    }

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