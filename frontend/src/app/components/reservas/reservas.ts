import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReservaService } from '../../services/reserva.service';

@Component({
  selector: 'app-reservas',
  imports: [FormsModule],
  templateUrl: './reservas.html',
  styleUrl: './reservas.scss',
})
export class Reservas {

  // datos del formulario
  reserva = {
    idEntrenador: '',
    fechaReserva: '',
    horaInicio: ''
  }

  // inyectar servicio de reservas
  constructor(private reservaService: ReservaService) { }

  // metodo para crear reserva
  crearReserva() {
    console.log('Creando reserva:', this.reserva);
  }

}
