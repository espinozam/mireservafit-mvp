package ifc33b.dwesc.mireservafit.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "cliente")
@DiscriminatorValue("CLIENTE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends Usuario {

    @Column(name = "fecha_nacimiento")
    private LocalDate fecha_nacimiento;
}