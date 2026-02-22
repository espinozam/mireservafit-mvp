package ifc33b.dwesc.mireservafit.service;

import ifc33b.dwesc.mireservafit.model.Usuario;
import ifc33b.dwesc.mireservafit.model.Cliente;
import ifc33b.dwesc.mireservafit.model.Entrenador;
import ifc33b.dwesc.mireservafit.repository.UsuarioRepository;
import ifc33b.dwesc.mireservafit.repository.ClienteRepository;
import ifc33b.dwesc.mireservafit.repository.EntrenadorRepository;
import ifc33b.dwesc.mireservafit.dto.LoginRequest;
import ifc33b.dwesc.mireservafit.dto.RegisterRequest;
import ifc33b.dwesc.mireservafit.dto.UsuarioResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final EntrenadorRepository entrenadorRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioResponse login(LoginRequest request, HttpSession session) {

        // obtener usaurio
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("No existe un usuario con ese email"));

        // comprobar contraseña
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // guardar sewsión del usuario autenticado
        session.setAttribute("usuario_id", usuario.getId());
        session.setAttribute("usuario_rol", usuario.getRol());

        // devolver datos del usuario
        return new UsuarioResponse(usuario.getId(), usuario.getNombre(), usuario.getEmail(), usuario.getRol());
    }

    public UsuarioResponse register(RegisterRequest request) {

        // comprobar si ya existe un usuario con el mismo email
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Ya existe un usuario con ese email");
        }

        // obtener rol
        String rol = request.getRol().trim().toUpperCase();

        // validar cammpo rol, opciones válidas: CLIENTE, ENTRENADOR
        if (!rol.equals("CLIENTE") && !rol.equals("ENTRENADOR")) {
            throw new RuntimeException("Rol inválido. Debe ser CLIENTE o ENTRENADOR");
        }

        // CLIENTE
        if (rol.equals("CLIENTE")) {

            // obtener fechaNacimiento y validar que no sea nula
            if (request.getFechaNacimiento() == null) {
                throw new RuntimeException("fechaNacimiento es obligatorio para CLIENTE");
            }

            // validar edad mínima de 16 años
            if (request.getFechaNacimiento().isAfter(LocalDate.now().minusYears(16))) {
                throw new RuntimeException("Debes tener al menos 16 años para registrarte");
            }

            // Instanciar cliente
            Cliente cliente = new Cliente();

            // setear datos
            cliente.setNombre(request.getNombre());
            cliente.setEmail(request.getEmail());
            cliente.setPassword(passwordEncoder.encode(request.getPassword()));
            cliente.setRol("CLIENTE");
            cliente.setFecha_nacimiento(request.getFechaNacimiento());

            // guardar cliente en la base de datos
            Cliente saved = clienteRepository.save(cliente);
            return new UsuarioResponse(saved.getId(), saved.getNombre(), saved.getEmail(), saved.getRol());
        }

        // ENTRENADOR

        // obtener especialidad y validar que no sea nula ni vacía
        if (request.getEspecialidad() == null || request.getEspecialidad().isBlank()) {
            throw new RuntimeException("especialidad es obligatorio para ENTRENADOR");
        }

        // instanciar entrenador
        Entrenador entrenador = new Entrenador();

        // setaer datos
        entrenador.setNombre(request.getNombre());
        entrenador.setEmail(request.getEmail());
        entrenador.setPassword(passwordEncoder.encode(request.getPassword()));
        entrenador.setRol("ENTRENADOR");
        entrenador.setEspecialidad(request.getEspecialidad());

        Entrenador saved = entrenadorRepository.save(entrenador);
        return new UsuarioResponse(saved.getId(), saved.getNombre(), saved.getEmail(), saved.getRol());
    }

    // comprobar session
    public UsuarioResponse me(HttpSession session) {

        // obtener id de usuario de la sesión
        Object usuarioId = session.getAttribute("usuario_id");

        // comprobar si usuario está autenticado
        if (usuarioId == null) {
            throw new RuntimeException("No hay sesión activa");
        }

        // obtener usuario de la base de datos
        Usuario usuario = usuarioRepository.findById((Integer) usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // devolver datos del usuario
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol()
        );
    }
}
