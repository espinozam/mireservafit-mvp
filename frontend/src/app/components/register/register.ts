import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

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
  constructor() {}

  // metodo para registrar
  registrar() {}
}

