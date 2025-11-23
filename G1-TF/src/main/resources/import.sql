INSERT INTO roles (name) VALUES ('ROLE_PACIENTE');
INSERT INTO roles (name) VALUES ('ROLE_PROFESIONALSALUD');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO users(username, password) VALUES ('paciente1','$2a$12$75bOrec9bALa4Vhptfso2Oh8/WWeMn0VwybKaa8ljqnszsVkaNrI2');
INSERT INTO users(username, password) VALUES ('profesional1','$2a$12$75bOrec9bALa4Vhptfso2Oh8/WWeMn0VwybKaa8ljqnszsVkaNrI2');
INSERT INTO users(username, password) VALUES ('admin','$2a$12$75bOrec9bALa4Vhptfso2Oh8/WWeMn0VwybKaa8ljqnszsVkaNrI2');
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1); -- user1 with ROLE_USER
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 3); -- admin with ROLE_ADMIN

-- 1. PACIENTES
INSERT INTO paciente (id_paciente, nombres, apellidos, dni, fecha_nacimiento, sexo, direccion, telefono, email, tipo_sangre, alergias, antecedentes) VALUES (1, 'Juan', 'Pérez', '12345678', '1990-05-10', 'M', 'Av Lima 123', '999111222', 'juan@example.com', 'O+', 'Ninguna', 'Sin antecedentes'), (2, 'María', 'Lopez', '87654321', '1985-09-20', 'F', 'Av Perú 555', '988777444', 'maria@example.com', 'A+', 'Alergia a penicilina', 'Asma'), (3, 'Carlos', 'Ramos', '11223344', '2000-01-15', 'M', 'Av Brasil 888', '900111333', 'carlos@example.com', 'B-', 'Polen', 'Sin antecedentes');

-- 2. PROFESIONALES SALUD
INSERT INTO profesional_salud (id_profesional, nombres, apellidos, especialidad, colegiatura, email, telefono) VALUES (1, 'Ana', 'Torres', 'Cardiología', 'CMP12345', 'ana.torres@cm.com', '955888111'), (2, 'Luis', 'Gomez', 'Pediatría', 'CMP56789', 'luis.gomez@cm.com', '944222333'), (3, 'Elena', 'Vargas', 'Dermatología', 'CMP98765', 'elena.vargas@cm.com', '933444555');

-- 3. CENTRO MEDICO (SUPOSICIÓN)
INSERT INTO centro_medico (id_centro_medico, nombre_centro, direccion, telefono) VALUES (1, 'Centro Médico Sur', 'Av. La Salud 123','999999999');

-- 4. CONSULTAS
INSERT INTO consulta (id_consulta, id_paciente, id_profesional, id_centro, fecha_consulta, diagnostico, receta) VALUES (1, 1, 1, 1, '2024-11-01'), (2, 2, 2, 1, '2024-11-05', 'Fiebre', 'Paracetamol de 500'), (3, 3, 3, 1, '2024-11-10', 'Tos', 'Ibuprofeno de 500');
