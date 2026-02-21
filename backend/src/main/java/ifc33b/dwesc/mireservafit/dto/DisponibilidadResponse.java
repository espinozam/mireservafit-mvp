package ifc33b.dwesc.mireservafit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class DisponibilidadResponse {
    // horas ocupadas
    private List<String> horasOcupadas; // "10:00", "11:00"...
}