package ifc33b.dwesc.mireservafit.controller;

import ifc33b.dwesc.mireservafit.model.Reserva;
import ifc33b.dwesc.mireservafit.dto.ReservaResponse;
import ifc33b.dwesc.mireservafit.dto.DashboardResponse;
import ifc33b.dwesc.mireservafit.service.ReservaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/entrenador")
@RequiredArgsConstructor
public class EntrenadorController {

    // usar el servicio de autenticacion
    private final ReservaService reservaService;
    private final HttpSession session;

    //endpoint para obtener los datos del usuario autenticado
    @GetMapping("/aguenda")
    public ResponseEntity<List<ReservaResponse>> agendaSemanal(HttpSession session) {
        // llamar al servicio y obtener la agenda semanal del entrenador autenticado
        List<ReservaResponse> response = reservaService.listarAgendaEntrenadorSemana(session);

        // devolver agenda de reservas y codigo 200 OK
        return ResponseEntity.ok(response);
    }

    // endpoint dashboard de entrenador
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard(HttpSession session) {
        // obtener datos para el dashboard
        DashboardResponse response = reservaService.obtenerDashboardEntrenador(session);

        // devolver datos del dashboard y codigo 200 OK
        return ResponseEntity.ok(response);
    }

}
