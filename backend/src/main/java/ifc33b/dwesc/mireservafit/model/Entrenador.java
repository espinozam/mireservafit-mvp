package ifc33b.dwesc.mireservafit.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "entrenador")
@DiscriminatorValue("ENTRENADOR")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entrenador extends Usuario {

    private String especialidad;
}