package ifc33b.dwesc.mireservafit.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    // atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne // un cliente tiene una o muchas reservas
    @JoinColumn(name = "id_cliente", nullable = false) // referencia id_cliente al id del cliente
    private Cliente cliente;

    @ManyToOne // un entrenador tiene una o muchas reservas
    @JoinColumn(name = "id_entrenador", nullable = false) // referencia columna id_entrenador al id del entrenador
    private Entrenador entrenador;

    @Column(name = "fecha_reserva", nullable = false)
    private LocalDate fechaReserva;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    private String estado;
}
