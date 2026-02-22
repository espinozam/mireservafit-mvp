import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  imports: [CommonModule, FormsModule],
  templateUrl: './register.html',
  styleUrl: './register.scss',
})
export class Register {

  // atributos
  nombre = '';
  email = '';
  password = '';
  rol = "CLIENTE";

  fechaNacimiento = '';
  especialidad = '';

  // constructor
    constructor(
      private authService: AuthService,
      private router: Router
    ) {}

  // metodo para registrar
  registrar() {
    // llamar al servicio para registrar
    this.authService.register({
      nombre: this.nombre,
      email: this.email,
      password: this.password,
      rol: this.rol,
      fechaNacimiento: this.fechaNacimiento,
      especialidad: this.especialidad
    }).subscribe({
      next: () =>  {
        // redirigir a login
        this.router.navigate(['/login']);
        alert('Usuario registrado correctamente!');
      },
      error: (error) => alert('Error: ' + error.error.message)
    });
  }
}

