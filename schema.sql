CREATE TABLE paciente (
    id_paciente SERIAL PRIMARY KEY,
    nombres VARCHAR(100),
    apellidos VARCHAR(100),
    dni CHAR(8) UNIQUE NOT NULL,
    fecha_nacimiento DATE,
    sexo CHAR(1),
    direccion VARCHAR(200),
    telefono VARCHAR(15),
    email VARCHAR(100),
    tipo_sangre VARCHAR(5),
    alergias TEXT
);

CREATE TABLE profesional_salud (
    id_profesional SERIAL PRIMARY KEY,
    nombres VARCHAR(100),
    apellidos VARCHAR(100),
    especialidad VARCHAR(100),
    colegiatura VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(100),
    telefono VARCHAR(15)
);

CREATE TABLE centro_medico (
    id_centro SERIAL PRIMARY KEY,
    nombre VARCHAR(150),
    direccion VARCHAR(200),
    telefono VARCHAR(15)
);

CREATE TABLE consulta (
    id_consulta SERIAL PRIMARY KEY,
    id_paciente INT NOT NULL REFERENCES paciente(id_paciente) ON DELETE CASCADE,
    id_profesional INT NOT NULL REFERENCES profesional_salud(id_profesional) ON DELETE CASCADE,
    id_centro INT NOT NULL REFERENCES centro_medico(id_centro) ON DELETE CASCADE,
    fecha_consulta DATE,
    diagnostico TEXT,
    tratamiento TEXT
);

CREATE TABLE diagnostico (
    id_diagnostico SERIAL PRIMARY KEY,
    id_consulta INT NOT NULL REFERENCES consulta(id_consulta) ON DELETE CASCADE,
    descripcion TEXT,
    codigo_cie10 VARCHAR(10)
);

CREATE TABLE medicamento (
    id_medicamento SERIAL PRIMARY KEY,
    nombre VARCHAR(100),
    descripcion VARCHAR(200),
    dosis VARCHAR(50)
);

CREATE TABLE receta (
    id_receta SERIAL PRIMARY KEY,
    id_consulta INT NOT NULL REFERENCES consulta(id_consulta) ON DELETE CASCADE,
    fecha_emision DATE
);

CREATE TABLE receta_medicamento (
    id_receta INT NOT NULL REFERENCES receta(id_receta) ON DELETE CASCADE,
    id_medicamento INT NOT NULL REFERENCES medicamento(id_medicamento) ON DELETE CASCADE,
    indicaciones TEXT,
    PRIMARY KEY (id_receta, id_medicamento)
);

CREATE TABLE usuario (
    id_usuario SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    rol VARCHAR(20) NOT NULL, -- PACIENTE, PROFESIONAL, ADMIN
    id_paciente INT REFERENCES paciente(id_paciente) ON DELETE SET NULL,
    id_profesional INT REFERENCES profesional_salud(id_profesional) ON DELETE SET NULL
);
