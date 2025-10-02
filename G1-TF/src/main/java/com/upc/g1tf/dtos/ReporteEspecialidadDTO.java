package com.upc.g1tf.dtos;

public class ReporteEspecialidadDTO {
    private String especialidad;
    private Long pacientes;
    private Long consultas;

    public ReporteEspecialidadDTO(String especialidad, Long pacientes, Long consultas) {
        this.especialidad = especialidad;
        this.pacientes = pacientes;
        this.consultas = consultas;
    }

    public String getEspecialidad() {
        return especialidad;
    }
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Long getPacientes() {
        return pacientes;
    }
    public void setPacientes(Long pacientes) {
        this.pacientes = pacientes;
    }

    public Long getConsultas() {
        return consultas;
    }
    public void setConsultas(Long consultas) {
        this.consultas = consultas;
    }
}
