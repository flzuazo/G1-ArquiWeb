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
-- Consultas del 2024
INSERT INTO consulta (id_consulta, id_paciente, id_profesional, id_centro, fecha_consulta, diagnostico, receta)VALUES (101, 100, 100, 100, '2024-03-15', 'Resfriado', 'Paracetamol');
INSERT INTO consulta (id_consulta, id_paciente, id_profesional, id_centro, fecha_consulta, diagnostico, receta)VALUES (102, 100, 100, 100, '2024-07-20', 'Dolor de cabeza', 'Ibuprofeno');
INSERT INTO consulta (id_consulta, id_paciente, id_profesional, id_centro, fecha_consulta, diagnostico, receta)VALUES (103, 100, 100, 100, '2024-05-10', 'Gripe', 'Amoxicilina');
INSERT INTO consulta (id_consulta, id_paciente, id_profesional, id_centro, fecha_consulta, diagnostico, receta)VALUES (104, 100, 100, 100, '2024-11-05', 'Indigestión', 'Omeprazol');
INSERT INTO consulta (id_consulta, id_paciente, id_profesional, id_centro, fecha_consulta, diagnostico, receta)VALUES (105, 100, 100, 100, '2024-02-22', 'Tensión muscular', 'Diclofenaco');
INSERT INTO consulta (id_consulta, id_paciente, id_profesional, id_centro, fecha_consulta, diagnostico, receta)VALUES (106, 100, 100, 100, '2024-09-25', 'Mareos', 'Betahistina');
INSERT INTO consulta (id_consulta, id_paciente, id_profesional, id_centro, fecha_consulta, diagnostico, receta)VALUES (107, 100, 100, 100, '2024-07-03', 'Eczema', 'Hidrocortisona');
INSERT INTO consulta (id_consulta, id_paciente, id_profesional, id_centro, fecha_consulta, diagnostico, receta)VALUES (108, 100, 100, 100, '2024-07-12', 'Hipertensión', 'Amlodipino');
INSERT INTO consulta (id_consulta, id_paciente, id_profesional, id_centro, fecha_consulta, diagnostico, receta)VALUES (109, 100, 100, 100, '2024-06-17', 'Asma', 'Salbutamol');
INSERT INTO consulta (id_consulta, id_paciente, id_profesional, id_centro, fecha_consulta, diagnostico, receta)VALUES (110, 100, 100, 100, '2024-03-21', 'Dolor lumbar', 'Paracetamol');

-- Consultas del 2025
INSERT INTO consulta (id_consulta, id_paciente, id_profesional, id_centro, fecha_consulta, diagnostico, receta)VALUES (111, 100, 100, 100, '2025-09-05', 'Conjuntivitis', 'Colirio');
INSERT INTO consulta (id_consulta, id_paciente, id_profesional, id_centro, fecha_consulta, diagnostico, receta)VALUES (112, 100, 100, 100, '2025-03-28', 'Insomnio', 'Melatonina');
INSERT INTO consulta (id_consulta, id_paciente, id_profesional, id_centro, fecha_consulta, diagnostico, receta)VALUES (113, 100, 100, 100, '2025-09-14', 'Anemia', 'Sulfato ferroso');
INSERT INTO consulta (id_consulta, id_paciente, id_profesional, id_centro, fecha_consulta, diagnostico, receta)VALUES (114, 100, 100, 100, '2025-09-30', 'Alergia estacional', 'Loratadina');
INSERT INTO consulta (id_consulta, id_paciente, id_profesional, id_centro, fecha_consulta, diagnostico, receta)VALUES (115, 100, 100, 100, '2025-03-12', 'Dolor abdominal', 'Omeprazol');
INSERT INTO consulta (id_consulta, id_paciente, id_profesional, id_centro, fecha_consulta, diagnostico, receta)VALUES (116, 100, 100, 100, '2025-01-18', 'Tos', 'Dextrometorfano');
INSERT INTO consulta (id_consulta, id_paciente, id_profesional, id_centro, fecha_consulta, diagnostico, receta)VALUES (117, 100, 100, 100, '2025-03-24', 'Migraña', 'Sumatriptán');
INSERT INTO consulta (id_consulta, id_paciente, id_profesional, id_centro, fecha_consulta, diagnostico, receta)VALUES (118, 100, 100, 100, '2025-04-02', 'Reflujo ácido', 'Esomeprazol');
INSERT INTO consulta (id_consulta, id_paciente, id_profesional, id_centro, fecha_consulta, diagnostico, receta)VALUES (119, 100, 100, 100, '2025-05-07', 'Otitis', 'Amoxicilina');
INSERT INTO consulta (id_consulta, id_paciente, id_profesional, id_centro, fecha_consulta, diagnostico, receta)VALUES (120, 100, 100, 100, '2025-03-25', 'Aftas bucales', 'Benzydamina');

