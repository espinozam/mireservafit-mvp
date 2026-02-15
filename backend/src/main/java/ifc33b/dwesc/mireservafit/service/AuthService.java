package ifc33b.dwesc.mireservafit.service;

import ifc33b.dwesc.mireservafit.model.Usuario;
import ifc33b.dwesc.mireservafit.repository.UsuarioRepository;
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

        // crear objeto
        Usuario usuario = new Usuario();

        // setear datos
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol("USER");

        usuarioRepository.save(usuario);

        return new UsuarioResponse(usuario.getId(), usuario.getNombre(), usuario.getEmail(), usuario.getRol());
    }
}
