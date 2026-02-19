package ifc33b.dwesc.mireservafit.repository;

import ifc33b.dwesc.mireservafit.model.Reserva;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    // Metodo para encontrar reservas por entrenador y fecha
    List<Reserva> findByEntrenadorIdAndFechaReserva(Integer entrenadorId, LocalDate fechaReserva);
}