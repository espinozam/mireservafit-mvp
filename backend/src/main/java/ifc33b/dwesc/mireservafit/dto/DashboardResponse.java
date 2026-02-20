package ifc33b.dwesc.mireservafit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardResponse {
    // atributos
    private Long totalSemana; // total de reservas de la semana
    private Long pendientes; // reservas CONFIRMADO por realizar
}