package com.upc.g1tf.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReporteCentroDTO {
    private String centro;
    private Long numeroConsultas;
    private Long numeroDoctores;
}
