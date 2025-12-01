INSERT INTO roles (name) VALUES ('ROLE_PACIENTE');
INSERT INTO roles (name) VALUES ('ROLE_PROFESIONALSALUD');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
-- 1. PACIENTES
INSERT INTO paciente (id_paciente, nombres, apellidos, dni, fecha_nacimiento, sexo, direccion, telefono, email, tipo_sangre, alergias, antecedentes) VALUES (100, 'Juan', 'Pérez', '12345678', '1990-05-10', 'M', 'Av Lima 123', '999111222', 'juan@example.com', 'O+', 'Ninguna', 'Sin antecedentes'), (102, 'María', 'Lopez', '87654321', '1985-09-20', 'F', 'Av Perú 555', '988777444', 'maria@example.com', 'A+', 'Alergia a penicilina', 'Asma'), (103, 'Carlos', 'Ramos', '11223344', '2000-01-15', 'M', 'Av Brasil 888', '900111333', 'carlos@example.com', 'B-', 'Polen', 'Sin antecedentes');

-- 2. PROFESIONALES SALUD
INSERT INTO profesional_salud (id_profesional, nombres, apellidos, especialidad, colegiatura, email, telefono) VALUES (100, 'Ana', 'Torres', 'Cardiología', 'CMP12345', 'ana.torres@cm.com', '955888111'), (2, 'Luis', 'Gomez', 'Pediatría', 'CMP56789', 'luis.gomez@cm.com', '944222333'), (3, 'Elena', 'Vargas', 'Dermatología', 'CMP98765', 'elena.vargas@cm.com', '933444555');

INSERT INTO users(id, username, password, id_paciente, id_profesional) VALUES (100,'paciente1','$2a$12$75bOrec9bALa4Vhptfso2Oh8/WWeMn0VwybKaa8ljqnszsVkaNrI2', 100, NULL);
INSERT INTO users(id, username, password, id_paciente, id_profesional) VALUES (200,'profesional1','$2a$12$75bOrec9bALa4Vhptfso2Oh8/WWeMn0VwybKaa8ljqnszsVkaNrI2', NULL, 100);
INSERT INTO users(id, username,password, id_paciente, id_profesional) VALUES (300,'admin','$2a$12$75bOrec9bALa4Vhptfso2Oh8/WWeMn0VwybKaa8ljqnszsVkaNrI2', NULL, NULL);
INSERT INTO user_roles (user_id, role_id) VALUES (100, 1); -- user1 with ROLE_USER
INSERT INTO user_roles (user_id, role_id) VALUES (200, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (300, 3); -- admin with ROLE_ADMIN


-- 3. CENTRO MEDICO (SUPOSICIÓN)
INSERT INTO centro_medico (id_centro_medico, nombre_centro, direccion, telefono) VALUES (100, 'Centro Médico Sur', 'Av. La Salud 123','999999999');

-- 4. CONSULTAS
INSERT INTO consulta (id_consulta, id_paciente, id_profesional, id_centro, fecha_consulta, diagnostico, receta) VALUES (100, 100, 100, 100, '2024-11-01','Alergia','Cetirizina');
