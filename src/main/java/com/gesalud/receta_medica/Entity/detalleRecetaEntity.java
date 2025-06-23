package com.gesalud.receta_medica.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "detalle_receta")
public class detalleRecetaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_receta", referencedColumnName = "id")
    private recetaEntity receta;

    @ManyToOne
    @JoinColumn(name = "id_medicamento", referencedColumnName = "id")
    private medicamentoEntity medicamento;

    @Column(name = "concentracion", length = 50)
    private String concentracion;

    @Column(name = "dosis", length = 50)
    private String dosis;

    @Column(name = "frecuencia", length = 50)
    private String frecuencia;

    @Column(name = "duracion", length = 50)
    private String duracion;

    @Column(name = "activo")
    private Boolean activo;
}
