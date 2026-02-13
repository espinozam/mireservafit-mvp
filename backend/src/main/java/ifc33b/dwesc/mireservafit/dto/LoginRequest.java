package ifc33b.dwesc.mireservafit.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        // atributos
        @Email
        @NotBlank(message = "Email es obligatorio")
        String email,

        @NotBlank(message = "Password es obligatorio")
        String password
) {}
