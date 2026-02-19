package ifc33b.dwesc.mireservafit.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaRequest {
    // atributos
    @NotNull(message = "Entrenador es obligatorio")
    private Integer idEntrenador;

    @NotNull(message = "Fecha de reserva es obligatorio")
    @FutureOrPresent(message = "La fecha de reserva no puede ser en el pasado")
    private LocalDate fechaReserva;

    @NotNull(message = "Hora de inicio es obligatoria")
    private LocalTime horaInicio;

    @NotNull(message = "Hora de fin es obligatoria")
    private LocalTime horaFin;
}