import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  imports: [CommonModule, FormsModule],
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
  ) {}

  // metodo para hacer login
  login() {
    this.authService.login({
      email: this.email,
      password: this.password
    }).subscribe({
      next: () =>  {
          alert('Inicio de sesión correcto');
        },
      error: () => alert('Login incorrecto')
    });
  }
}
