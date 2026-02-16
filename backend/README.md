# Backend

## Configuración de MySQL

El backend está configurado para usar MySQL por defecto.

1. Crea la base de datos y tablas ejecutando:

```bash
mysql -u root -p < ../database/schema.sql
```

2. Configura credenciales con variables de entorno (recomendado):

```bash
export DB_URL="jdbc:mysql://localhost:3306/mireservafit_bd?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
export DB_USERNAME="root"
export DB_PASSWORD="tu_password"
```

Si no defines variables, se usan estos valores por defecto:

- `DB_URL=jdbc:mysql://localhost:3306/mireservafit_bd?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC`
- `DB_USERNAME=root`
- `DB_PASSWORD=12345`

3. Levanta la aplicación:

```bash
./mvnw spring-boot:run
```