package com.gesalud.receta_medica.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class recetaDTO {
    private Long paciente;
    private Long medico;
    private Boolean disponible;
    private Boolean activo;
}
