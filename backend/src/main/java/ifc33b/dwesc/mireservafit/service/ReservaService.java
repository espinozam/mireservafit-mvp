package ifc33b.dwesc.mireservafit.service;

import ifc33b.dwesc.mireservafit.model.Cliente;
import ifc33b.dwesc.mireservafit.model.Entrenador;
import ifc33b.dwesc.mireservafit.model.Reserva;
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
import java.util.List;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ReservaService {

    // usar el repositorio para acceder a la base de datos
    private final ReservaRepository repository;
    private final ClienteRepository clienteRepository;
    private final EntrenadorRepository entrenadorRepository;

    // crear reserva
    public ReservaResponse crearReserva(ReservaRequest request, HttpSession session) {

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

        // reservas del entrenador en la fecha de la reserva
        List<Reserva> reservasEntrenador = repository.findByEntrenadorIdAndFechaReserva(entrenadorId, request.getFechaReserva());

        // hora de inicio y hora de fin de la nueva reserva
        LocalTime horaInicio = request.getHoraInicio();
        LocalTime horaFin = horaInicio.plusHours(1); // duración fija de 1 hora

        // comprobar que la hora de inicio es dentro del horario permitido (8:00 a 21:00)
        if (horaInicio.isBefore(LocalTime.of(8, 0)) ||
                horaFin.isAfter(LocalTime.of(21, 0))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La reserva debe ser entre las 8:00 y las 21:00");
        }

        // comprobar reservas actuales del entrenador no se solapan con la nueva reserva
        for (Reserva reserva : reservasEntrenador) {
            // existe solapamiento si:
            // horaInicio < horaFinReservaExistente && horaFin > horaInicioReservaExistente
            if (horaInicio.isBefore(reserva.getHoraFin()) && horaFin.isAfter(reserva.getHoraInicio())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "La reserva se solapa con otra reserva existente");
            }
        }

        // crear reserva
        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setEntrenador(entrenador);
        reserva.setFechaReserva(request.getFechaReserva());
        reserva.setHoraInicio(horaInicio);
        reserva.setHoraFin(horaFin);
        reserva.setEstado("CONFIRMADO");

        // guardar reserva en la base de datos
        Reserva reservaGuardada = repository.save(reserva);

        // devolver response
        return new ReservaResponse(
                reservaGuardada.getId(),
                reservaGuardada.getCliente().getNombre(),
                reservaGuardada.getEntrenador().getNombre(),
                reservaGuardada.getFechaReserva(),
                reservaGuardada.getHoraInicio(),
                reservaGuardada.getHoraFin(),
                reservaGuardada.getEstado()
        );
    }

    // listar reservas de un cliente
    public List<ReservaResponse> listarMisReservas(HttpSession session) {
        // Comprobar si cliente autenticado
        Integer clienteId = (Integer) session.getAttribute("usuario_id");
        if (clienteId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No estás autenticado");
        }

        // Comprobar si el cliente autenticado tiene rol de CLIENTE
        String rol = (String) session.getAttribute("usuario_rol");
        if (!"CLIENTE".equals(rol)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para ver reservas");
        }

        // comprobar si el cliente existe
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));

        // obtener reservas del cliente
        List<Reserva> reservasCliente = repository.findByClienteId(clienteId);

        // devolver response con las reservas del cliente
        return reservasCliente.stream()
                .map(reserva -> new ReservaResponse(
                        reserva.getId(),
                        reserva.getCliente().getNombre(),
                        reserva.getEntrenador().getNombre(),
                        reserva.getFechaReserva(),
                        reserva.getHoraInicio(),
                        reserva.getHoraFin(),
                        reserva.getEstado()
                ))
                .toList();
    }

    // cancelar reserva
    public void cancelarReserva(Integer id, HttpSession session) {
        // Comprobar si cliente autenticado
        Integer clienteId = (Integer) session.getAttribute("usuario_id");
        if (clienteId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No estás autenticado");
        }

        // Comprobar si el cliente autenticado tiene rol de CLIENTE
        String rol = (String) session.getAttribute("usuario_rol");
        if (!"CLIENTE".equals(rol)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para ver reservas");
        }

        // buscar reserva por id y cliente
        Reserva reserva = repository.findByIdAndClienteId(id, clienteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva no encontrada"));

        // si reserva está cancelada, no hacer nada (idempotente)
        if (reserva.getEstado().equals("CANCELADO")) {
            return;
        }
        // cancelar reserva
        reserva.setEstado("CANCELADO");

        // Guardar reserva cancelada en la base de datos para el historial de reservas del cliente
        repository.save(reserva);
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


