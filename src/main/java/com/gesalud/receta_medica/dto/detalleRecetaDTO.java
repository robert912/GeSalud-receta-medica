package com.gesalud.receta_medica.dto;

import lombok.Data;

@Data
public class detalleRecetaDTO {
    private Long receta;
    private Long medicamento;
    private String concentracion;
    private String dosis;
    private String frecuencia;
    private String duracion;
    private Boolean activo;
}
