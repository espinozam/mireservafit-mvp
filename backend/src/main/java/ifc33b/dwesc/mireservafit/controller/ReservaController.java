package ifc33b.dwesc.mireservafit.controller;

import ifc33b.dwesc.mireservafit.dto.ReservaRequest;
import ifc33b.dwesc.mireservafit.dto.ReservaResponse;
import ifc33b.dwesc.mireservafit.service.ReservaService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;
    private final HttpSession session;

    // Endpoint para obtener todas las reservas
    @GetMapping
    public ResponseEntity<List<ReservaResponse>> getAllReservas(HttpSession session) {

        // llamar al servicio para obtener todas las reservas
        List<ReservaResponse> reservas = reservaService.listarMisReservas(session);

        // devolver código 200 OK y la lista de reservas en el body de la respuesta
        return ResponseEntity.ok(reservas);
    }

    // Endpoint para crear una nueva reserva
    @PostMapping("crear")
    public ResponseEntity<ReservaResponse> createReserva(@Valid @RequestBody ReservaRequest request, HttpSession session) {
        // llamar al servicio para crear la reserva
        // devolver reserva creada en el body de la respuesta y código 200 OK
        ReservaResponse response = reservaService.crearReserva(request, session);
        return ResponseEntity.ok(response);
    }

    // Endpoint para eliminar una reserva por su id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReserva(@PathVariable Integer id, HttpSession session) {
        try {
            // llmar al servicio para eliminar/cancela la reserva
            // y devolver codigo 204 No Content
            reservaService.cancelarReserva(id, session);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // endpoint para obtener horas ocupadas de entrenador
    @GetMapping("/disponibilidad")
    public ResponseEntity<List<String>> getDisponibilidad(
            @RequestParam Integer entrenadorId,
            @RequestParam LocalDate fecha
    ) {
        List<String> horasOcupadas = reservaService.obtenerDisponibilidadEntrenador(entrenadorId, fecha);
        return ResponseEntity.ok(horasOcupadas);
    }
}