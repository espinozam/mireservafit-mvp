import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReservaService } from '../../services/reserva.service';
import { ReservaRequest } from '../../models/reserva-request.model';

@Component({
  selector: 'app-reservas',
  imports: [FormsModule],
  templateUrl: './reservas.html',
  styleUrl: './reservas.scss',
})
export class Reservas {

  // datos del formulario
  reserva: ReservaRequest = {
    idEntrenador: 0,
    fechaReserva: '',
    horaInicio: ''
  }

  // inyectar servicio de reservas
  constructor(private reservaService: ReservaService) { }

  // metodo para crear reserva
  crearReserva() {

    // llamar al servicio para crear reserva
    this.reservaService.createReserva(this.reserva).subscribe({
      next: (response) => {
        alert("Reserva creada con id: " + response.id);
      },
      error: (error) => {
        alert("Error: " + error.error.message); // mostrar mensaje de error del backend
      }
    });
  }

}
