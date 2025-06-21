package com.gesalud.receta_medica.dto;

import lombok.Data;

@Data
public class detalleRecetaDTO {
    private Long receta;
    private Long medicamento;
    private String cantidad;
    private String dosisDiaria;
    private Boolean activo;
}
