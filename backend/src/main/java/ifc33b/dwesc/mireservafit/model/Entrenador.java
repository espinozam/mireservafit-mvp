package ifc33b.dwesc.mireservafit.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "entrenador")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entrenador extends Usuario {

    // atributos
    private String especialidad;
}