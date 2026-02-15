package ifc33b.dwesc.mireservafit.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {
    // atributos
    private Integer id;
    private String nombre;
    private String email;
    private String rol;
}