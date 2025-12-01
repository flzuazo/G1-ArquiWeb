
package com.upc.g1tf.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReporteEspecialidadDTO {
    // Getters y setters
    private String especialidad;
    private Long cantidad;
    private Long total; // tercer campo que ya existía en la clase

    public ReporteEspecialidadDTO() {
    }

    // Constructor existente (mantener para compatibilidad)
    public ReporteEspecialidadDTO(String especialidad, Long cantidad, Long total) {
        this.especialidad = especialidad;
        this.cantidad = cantidad;
        this.total = total;
    }

    // Nuevo constructor pedido por el servicio
    public ReporteEspecialidadDTO(String especialidad, Long cantidad) {
        this.especialidad = especialidad;
        this.cantidad = cantidad;
        this.total = 0L; // valor por defecto
    }

}
