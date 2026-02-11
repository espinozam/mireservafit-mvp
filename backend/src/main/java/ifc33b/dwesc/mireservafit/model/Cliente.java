package ifc33b.dwesc.mireservafit.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends Usuario {

    // atributos
    private LocalDate fechaNacimiento;
}