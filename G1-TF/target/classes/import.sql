INSERT INTO roles (name) VALUES ('ROLE_PACIENTE');
INSERT INTO roles (name) VALUES ('ROLE_PROFESIONALSALUD');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO users(username, password) VALUES ('paciente1','$2a$12$fQ6jTbcCZp2USNF7j/Ezv.ztLrV88sEXPGeOmRPz5BWlRWFf2JpGW');
INSERT INTO users(username, password) VALUES ('profesional1','$2a$12$fQ6jTbcCZp2USNF7j/Ezv.ztLrV88sEXPGeOmRPz5BWlRWFf2JpGW');
INSERT INTO users(username, password) VALUES ('admin','$2a$12$fQ6jTbcCZp2USNF7j/Ezv.ztLrV88sEXPGeOmRPz5BWlRWFf2JpGW');
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1); -- user1 with ROLE_USER
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 3); -- admin with ROLE_ADMIN
-- Centro médico de ejemplo
INSERT INTO centro_medico (nombre, direccion, telefono)
VALUES ('Clínica Santa María', 'Av. Perú 1234', '01678900');

-- Medicamentos de ejemplo
INSERT INTO medicamento (nombre, dosis)
VALUES ('Paracetamol', '500mg'),
       ('Ibuprofeno', '400mg');