import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReservaService } from '../../services/reserva.service';
import { ReservaRequest } from '../../models/reserva-request.model';
import { ReservaResponse } from '../../models/reserva-response.model';

@Component({
  selector: 'app-reservas',
  imports: [CommonModule, FormsModule],
  templateUrl: './reservas.html',
  styleUrl: './reservas.scss',
})
export class Reservas implements OnInit {

  // datos del formulario
  reserva: ReservaRequest = {
    idEntrenador: 0,
    fechaReserva: '',
    horaInicio: ''
  }

  // lista de reservas
  reservas: ReservaResponse[] = [];

  // inyectar servicio de reservas
  constructor(private reservaService: ReservaService) { }

  // cargar al iniciar el componente
  ngOnInit() {
    this.cargarReservas();
  }

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

  // metodo para mostrar mis reservas
  cargarReservas(){
    this.reservaService.getReservas().subscribe({
      next: (response) => {
        this.reservas = response;
      },
      error: (error) => {
        alert("Error: " + error.error.message); // mostrar mensaje de error del backend
      }
    })
  }
}
