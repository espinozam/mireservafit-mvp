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
        try {
            UsuarioResponse response = authService.register(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
