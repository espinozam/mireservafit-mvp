import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ReservaRequest } from '../models/reserva-request.model';
import { ReservaResponse } from '../models/reserva-response.model';

@Injectable({
  providedIn: 'root',
})
export class ReservaService {

    // direccion a la api del backend (http://localhost:8080/api/auth)
    // ruta relativa para activar el proxy
    private apiUrl = '/api/reservas';

    // constructor
    constructor(private http: HttpClient) {}

    // metodo para crear reserva
    createReserva(reservaRequest: ReservaRequest): Observable<ReservaResponse> {
      return this.http.post<ReservaResponse>(`${this.apiUrl}/crear`, reservaRequest, { withCredentials: true });
    }

    // metodo para obtener mis reservas
    getReservas(): Observable<ReservaResponse[]> {
      return this.http.get<ReservaResponse[]>(this.apiUrl, { withCredentials: true} );
    }

    // metodo para canceñar reserva
    cancelReserva(id: number): Observable<void> {
      return this.http.delete<void>(`${this.apiUrl}/${id}`, { withCredentials: true })
    }
}
