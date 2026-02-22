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

### Funcionalidades clave

* **Gestión de usuarios y autenticación**

* Registro y login de clientes y entrenadores con contraseñas cifradas (bcrypt)

* Control de acceso mediante roles (CLIENTE / ENTRENADOR)

* **Gestión de reservas**
  
* Creación, listado y cancelación de reservas de 1 hora

* Validación de solapamientos y de la franja horaria laboral

* Cada cliente sólo puede manipular sus propias reservas

* **Dashboard del entrenador**

* Agenda semanal con las sesiones asignadas

* Estadísticas sobre sesiones totales de la semana y número de sesones pendientes

* **Disponibilidad visual**

* Diseño con Angular

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

### Opción 1 – Codespaces (recomendado)

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

## Autor

* **Edwin Espinoza Mercado** — [eespinozamercado@cifpfbmoll.eu](mailto:eespinozamercado@cifpfbmoll.eu)

* **GitHub:** [@espinozam](https://github.com/espinozam)

<div align="center"> Hecho con ❤️ para el módulo DWES / DWEC · 2026 </div>
