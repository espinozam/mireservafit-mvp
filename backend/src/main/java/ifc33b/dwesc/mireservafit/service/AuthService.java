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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final EntrenadorRepository entrenadorRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UsuarioResponse login(LoginRequest request) {

        // autenticar usuario con crenciales del usuario
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // generar token

        // obtener usuario para devolver su información en la respuesta
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // todo: devolver token en la respuesta
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
}
