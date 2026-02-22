package ifc33b.dwesc.mireservafit.controller;

import ifc33b.dwesc.mireservafit.model.Usuario;
import ifc33b.dwesc.mireservafit.dto.LoginRequest;
import ifc33b.dwesc.mireservafit.dto.RegisterRequest;
import ifc33b.dwesc.mireservafit.dto.UsuarioResponse;
import ifc33b.dwesc.mireservafit.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    // usar el servicio de autenticacion
    private final AuthService authService;
    private final HttpSession session;

    @PostMapping("/login")
    public ResponseEntity<UsuarioResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            UsuarioResponse response = authService.login(request, session);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> register(@Valid @RequestBody RegisterRequest request) {
        UsuarioResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    //endpoint para obtener los datos del usuario autenticado
    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> me(HttpSession session) {
        return ResponseEntity.ok(authService.me(session));
    }

    // logout
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        // destruir/invalidar sesión
        session.invalidate();

        // devolver respuesta vacía con código 200 OK
        return ResponseEntity.ok().build();
    }
}
