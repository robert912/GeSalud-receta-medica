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

    @Column(name = "cantidad_recetada", length = 50)
    private String cantidadRecetada;

    @Column(name = "dosis_diaria", length = 50)
    private String dosisDiaria;

    @Column(name = "activo")
    private Boolean activo;
}
