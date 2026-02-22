import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {

  // atributos
  email = '';
  password = '';

  // constructor
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  // metodo para hacer login
  login() {
    this.authService.login({
      email: this.email,
      password: this.password
    }).subscribe({
      next: (response) =>  {
        console.log('Login exitoso:', response);
          // comprobabos rol y redirigir a la página principal o dashboard
          if (response.rol == 'CLIENTE') {
            this.router.navigate(['/reservas']);
          } else if (response.rol == 'ENTRENADOR') {
            this.router.navigate(['/dashboard']);
          }
          alert('Inicio de sesión correcto');
        },
      error: (error) => alert('Login incorrecto! Error: ' + error.error.message)
    });
  }
}
