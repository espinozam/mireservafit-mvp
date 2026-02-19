package ifc33b.dwesc.mireservafit.service;

import ifc33b.dwesc.mireservafit.model.Cliente;
import ifc33b.dwesc.mireservafit.model.Entrenador;
import ifc33b.dwesc.mireservafit.dto.ReservaRequest;
import ifc33b.dwesc.mireservafit.dto.ReservaResponse;
import ifc33b.dwesc.mireservafit.repository.ReservaRepository;
import ifc33b.dwesc.mireservafit.repository.ClienteRepository;
import ifc33b.dwesc.mireservafit.repository.EntrenadorRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.RequiredArgsConstructor;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ReservaService {

    // usar el repositorio para acceder a la base de datos
    private final ReservaRepository repository;
    private final ClienteRepository clienteRepository;
    private final EntrenadorRepository entrenadorRepository;

    // crear reserva
    public ReservaRequest crearReserva(ReservaRequest request, HttpSession session) {

        // comprobar que horaFin debe ser mayor que horaInicio
        if (request.getHoraFin().isBefore(request.getHoraInicio()) || request.getHoraFin().equals(request.getHoraInicio())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La hora de fin debe ser mayor que la hora de inicio");
        }

        // obtener id del cliente autenticado de la sesión
        Integer clienteId = (Integer) session.getAttribute("usuario_id");
        if (clienteId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No estás autenticado");
        }

        String rol = (String) session.getAttribute("usuario_rol");

        // comprobar que el cliente autenticado tiene rol de CLIENTE
        if (!"CLIENTE".equals(rol)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para crear reservas");
        }

        // obtener cliente a partir del id del cliente autenticado
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));

        // obtener id del entrenador
        Integer entrenadorId = request.getIdEntrenador();

        // obtener entrenador del request
        Entrenador entrenador = entrenadorRepository.findById(entrenadorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entrenador no encontrado"));

        return null;
    }

    // listar reservas de un cliente
    public int listarMisReservas() {
        return 0;
    }

    // cancelar reserva
    public int cancelarReserva() {
        return 0;
    }

    // listar agenda de un entrenador en una semana
    public int listarAgendaEntrenadorSemana() {
        return 0;
    }

    // editar reserva
    public int editarReserva() {
        return 0;
    }

}


