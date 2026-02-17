import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  // direccion a la api del backend (http://localhost:8080/api/auth)
  // ruta relativa para activar el proxy
  private apiUrl = '/api/auth';

  // constructor
  constructor(private http: HttpClient) {}

  // metodo para hacer login (crea HttpSession en el backend)
  login(credenciales: any) {
    return this.http.post<any>(
      `${this.apiUrl}/login`,
      credenciales,
      { withCredentials: true } // enviar cookie de session
    );
  }

  // metodo para registrar usuario
  register(datos: any) {
    return this.http.post<any>(
      `${this.apiUrl}/register`,
      datos,
      { withCredentials: true } // backend inicia sesión tras registrar
    );
  }
}
