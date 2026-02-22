[Desarrollado con Spring-Boot](https://spring.io/projects/spring-boot)  
[Angular](https://angular.io/)  
[MySQL](https://www.mysql.com/)

# MiReservaFit – MVP

*Proyecto final de los módulos* **DWES** (Desarrollo Web en Entorno Servidor) & **DWEC** (Desarrollo Web en Entorno Cliente)

**CFGS Desarrollo de Aplicaciones Web (DAW)** — CIFP Francesc de Borja Moll

**Autor:** Edwin Espinoza Mercado

*Curso*: 2025‑2026

---

## Introducción

**MiReservaFit** es un sistema para digitalizar la reserva de entrenamientos personales en gimnasios.

### Funcionalidades añadidas (MVP)

**Gestión de usuarios y autenticación**
* Registro y login de clientes y entrenadores con contraseñas cifradas (bcrypt)
* Control de acceso mediante roles (CLIENTE / ENTRENADOR)

**Gestión de reservas**
* Creación, listado y cancelación de reservas de 1 hora
* Validación de solapamientos y de la franja horaria laboral
* Cada cliente sólo puede manipular sus propias reservas

**Dashboard del entrenador**
* Agenda semanal con las sesiones asignadas
* Estadísticas sobre sesiones totales de la semana y número de sesones pendientes

---

## Tecnologías usadas

| Capa | Tecnología principal        | Detalles                                                                  |
| :---- |:----------------------------|:--------------------------------------------------------------------------|
| **Backend** | Java 21 / Spring Boot 3     | API REST (servlets), validación de datos, acceso a BD con Spring Data JPA |
| **Autenticación** | Sesiones de HttpSession     | Contraseñas cifradas con BCrypt                                           |
| **Frontend SPA** | Angular 20 (Typescript)     | Formularios reactivos, rutas protegidas                                   |
| **Base de datos** | MySQL 8 (Soporta MariaDB)   | Relaciones 1:N entre usuarios y reservas                                  |
| **ORM/Acceso a datos** | Hibernate / Spring Data JPA | Mapeo de entidades, consultas personalizadas                              |
| **Docker** | Docker Compose              | Orquestación de contenedores para Base de Datos                           |
| **Dev Container** | GitHub Codespaces           | Plantilla preparada con Java 21 y Node 18                                 |

---

## Requisitos Previos

* **Git**

* **Java 17** o superior

* **Node.js 18** o superior (para el frontend)

* **Maven 3.8+**

* **Docker** y **Docker Compose** (para la base de datos local)

Si trabajas en **GitHub Codespaces** no necesitas instalar nada: el contenedor dev instala automáticamente todas las dependencias.

---

## Puesta en marcha rápida

### Opción 1 – Codespaces

1. Haz un *fork* del repositorio y abre un Codespace.

2. Espera a que se inicialice el contenedor (se instalarán automáticamente Java, Maven y Node).

3. En una terminal integrada ejecuta:

* Inicia el backend
```
cd backend
mvn spring-boot:run
```

* En otra terminal inicia el frontend:

```
cd frontend
ng serve
```

Sigue el enlace que te da CodeSpaces - http://localhost:4200 para acceder a la aplicación.

### Opción 2 – Instalación local con Docker

1. **Clonar el repositorio**

```bash
git clone https://github.com/espinozam/mireservafit-mvp.git  
cd mireservafit-mvp
```

2. **Arrancar la base de datos**

El proyecto incluye un fichero docker-compose.yml en la raíz que levanta un contenedor de MySQL 8 con los datos iniciales.

```bash
docker compose up -d
```

Esto creará la base de datos mireservafit con las tablas usuarios y reservas y un par de usuarios de prueba.

> Para limpiar la base de datos: docker compose down -v

1. **Compilar y ejecutar el backend**

* Inicia el backend
```
cd backend
mvn spring-boot:run
```

* En otra terminal inicia el frontend:

```
cd frontend
ng serve
```

La aplicación se servirá en http://localhost:4200 y consumirá la API REST del backend en http://localhost:8080.

## Prueba de endpoins con Postman

Url base para el backend (local):
* `baseUrl = http://localhost:8080`

--- 

## 1 Auth

### 1.1 Register (público)

**POST** `/api/auth/register`
Headers:

* `Content-Type: application/json`

Respuesta – Cliente:

```json
{
  "nombre": "Xavier Sastre",
  "email": "xavier@mail.com",
  "password": "123456",
  "rol": "CLIENTE",
  "fechaNacimiento": "1995-03-12"
}
```

Respuesta – Entrenador:

```json
{
  "nombre": "Edwin Espinoza",
  "email": "edwin@mail.com",
  "password": "123456",
  "rol": "ENTRENADOR",
  "especialidad": "Funcional"
}
```

Respuestas esperadas:

* **200 OK** o **201 Created** (según tu controller)

```json
{
  "id": 1,
  "nombre": "Xavier Sastre",
  "email": "xavier@mail.com",
  "rol": "CLIENTE"
}
```

* **400 Bad Request** (validación / email duplicado)

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Email ya registrado"
}
```

---

### 1.2 Login (público)

**POST** `/api/auth/login`
Headers:

* `Content-Type: application/json`

Body:

```json
{
  "email": "xavier@mail.com",
  "password": "123456"
}
```

Respuesta esperada:

* **200 OK**

```json
{
  "id": 1,
  "nombre": "Xavier Sastre",
  "email": "xavier@mail.com",
  "rol": "CLIENTE"
}
```

Notas Postman:

* En la respuesta debe aparecer cookie `JSESSIONID` (Postman la guardará).

---

### 1.3 Me (protegido)

**GET** `/api/auth/me`

Respuesta esperada:

* **200 OK**

```json
{
  "id": 1,
  "nombre": "Xavier Sastre",
  "email": "xavier@mail.com",
  "rol": "CLIENTE"
}
```

Si no hay sesión:

* **401 Unauthorized**

---

### 1.4 Logout (protegido)

**POST** `/api/auth/logout`

Respuesta esperada:

* **200 OK** o **204 No Content**

Después de esto, `GET /api/auth/me` debe dar **401**.

---

## 2 Reservas

### 2.1 Crear reserva (solo CLIENTE)

**POST** `/api/reservas/crear`

Headers:

* `Content-Type: application/json`

Body (ReservaRequest):

```json
{
  "idEntrenador": 3,
  "fechaReserva": "2026-03-01",
  "horaInicio": "10:00"
}
```

Respuesta esperada:

* **200 OK**

```json
{
  "id": 10,
  "nombreCliente": "Xavier Sastre",
  "nombreEntrenador": "Edwin Espinoza",
  "fechaReserva": "2026-03-01",
  "horaInicio": "10:00",
  "horaFin": "11:00",
  "estado": "CONFIRMADO"
}
```

Errores esperados:

* **401 Unauthorized** si no estás logueado
* **403 Forbidden** si el rol no es CLIENTE
* **400 Bad Request** si fuera de horario / datos inválidos
* **409 Conflict** si solapamiento

```json
{
  "status": 409,
  "error": "Conflict",
  "message": "La reserva se solapa con otra reserva existente",
  "path": "/api/reservas"
}
```

---

### 2.2 Listar mis reservas (CLIENTE)

**GET** `/api/reservas`

Respuesta esperada:

* **200 OK**

```json
[
  {
    "id": 10,
    "nombreCliente": "Xavier Sastre",
    "nombreEntrenador": "Edwin Espinoza",
    "fechaReserva": "2026-03-01",
    "horaInicio": "10:00",
    "horaFin": "11:00",
    "estado": "CONFIRMADO"
  }
]
```

---

### 2.3 Cancelar reserva (soft delete, solo propietario)

**DELETE** `/api/reservas/{{reservaId}}`

Respuesta esperada:

* **204 No Content**

Errores:

* **401** si no hay sesión
* **403** si no eres CLIENTE o no eres el propietario
* **404** si no existe o no pertenece al cliente
* **400** si ya está cancelada (si lo implementaste)

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "La reserva ya está cancelada"
}
```

---

## 3 Disponibilidad de entrenador (huecos ocupados)

### 3.1 Obtener horas ocupadas de un entrenador en una fecha

**GET** `/api/entrenadores/aguenda/3`

Respuesta esperada:

* **200 OK**

```json
["10:00", "11:00", "18:00"]
```

Errores:

* **404** si entrenador no existe
* **400** si fecha inválida

---

## Autor

* **Edwin Espinoza Mercado** — [eespinozamercado@cifpfbmoll.eu](mailto:eespinozamercado@cifpfbmoll.eu)

* **GitHub:** [@espinozam](https://github.com/espinozam)

<div align="center"> Hecho con ❤️ para el módulo DWES / DWEC · 2026 </div>
