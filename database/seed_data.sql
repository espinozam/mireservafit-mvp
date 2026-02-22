
-- usuarios cliente y entrenador
INSERT INTO usuario VALUES
(1,'Xavier Sastre','xavier@mail.com','$2a$10$32CCkGIQsH2u9Y0Lca2kre7App.39fY.KZCuyQqSOemjbbw7ynmYS','CLIENTE','2026-02-22 11:55:23','2026-02-22 11:55:23'),
(2,'Katia Huecas','katia@mail.com','$2a$10$M4NFrVBVIQHOFqI7zQwgLevf/WuRzAyfsiIWEaD0lxb4k7yXXJ.nS','CLIENTE','2026-02-22 11:55:36','2026-02-22 11:55:36'),
(3,'Edwin Espinoza','edwin@mail.com','$2a$10$6XD1egi9JASnkyAJVRYJWupA2Bv6hh9NHbjZyDVKDINz2pIAsH4QG','ENTRENADOR','2026-02-22 11:55:57','2026-02-22 11:55:57'),
(4,'Miquel','miquel@mail.com','$2a$10$GLDoCO99lz7/akCf2H9rjOtJcPdGjjLfo5OXLk4XEdEzTthFG79/K','ENTRENADOR','2026-02-22 11:57:27','2026-02-22 11:57:27');

-- Datos de cliente
INSERT INTO cliente VALUES
(1,'1995-03-12'),
(2,'1998-07-25');

-- Entrenadores
INSERT INTO `entrenador` VALUES
(3,'Musculación'),
(4,'Crossfit');

-- Reservas

-- Reservas de sesiones de Xavier
-- Reservas de sesiones de Katia
INSERT INTO `reserva` VALUES
(1,1,3,'2026-03-01','10:00:00','11:00:00','CONFIRMADO'),
(2,1,4,'2026-03-02','12:00:00','13:00:00','CONFIRMADO'),
(3,1,3,'2026-03-03','09:00:00','10:00:00','CANCELADO'),

(4,2,3,'2026-03-01','11:00:00','12:00:00','CONFIRMADO'),
(5,2,4,'2026-03-02','18:00:00','19:00:00','CONFIRMADO'),
(6,2,3,'2026-03-04','15:00:00','16:00:00','CONFIRMADO');