package ifc33b.dwesc.mireservafit.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    // atributos
    @Email
    @NotBlank(message = "Email es obligatorio")
    String email;

    @NotBlank(message = "Password es obligatorio")
    String password;
}