package ifc33b.dwesc.mireservafit.service;

import ifc33b.dwesc.mireservafit.model.Usuario;
import ifc33b.dwesc.mireservafit.model.Cliente;
import ifc33b.dwesc.mireservafit.repository.UsuarioRepository;
import ifc33b.dwesc.mireservafit.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;

    public Cliente crearCliente(Usuario usuario, LocalDate fechaNacimiento) {

        usuario.setPassword(usuario.getPassword());
        //usuario.setRol("CLIENTE");

        // crear usuario
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        Cliente cliente = new Cliente();
        cliente.setFecha_nacimiento(fechaNacimiento);

        return clienteRepository.save(cliente);
    }
}
