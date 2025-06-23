package com.gesalud.receta_medica.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "medicamento")
public class medicamentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "nombre", length = 120)
    private String nombre;

    @Column(name = "uso", length = 120)
    private String uso;

    @Column(name = "controlado")
    private Boolean controlado;

    @Column(name = "concentracion", length = 50)
    private String concentracion;

    @Column(name = "presentacion", length = 50)
    private String presentacion;

    @Column(name = "dosis", length = 50)
    private String dosis;

    @Column(name = "frecuencia", length = 50)
    private String frecuencia;

    @Column(name = "duracion", length = 50)
    private String duracion;

    @Column(name = "indicaciones",  length = 250)
    private  String indicaciones;

    @Column(name = "stock")
    private long stock = 0;

    @Column(name = "valor")
    private long valor = 0;

    @Column(name = "activo")
    private Boolean activo;
}
