import { Routes } from '@angular/router';
import { Register } from './components/register/register';
import { Login } from './components/login/login';
import { Reservas } from './components/reservas/reservas';

export const routes: Routes = [
  { path: 'register', component: Register },
  { path: 'login', component: Login },
  { path: 'reservas', component: Reservas },
  { path: '**', redirectTo: 'login' }
];
