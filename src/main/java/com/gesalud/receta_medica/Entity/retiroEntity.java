package com.gesalud.receta_medica.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "retiro")
public class retiroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne
    @JoinColumn(name = "id_detalle", referencedColumnName = "id")
    private detalleRecetaEntity detalleReceta;

    @ManyToOne
    @JoinColumn(name = "id_farmaceutico", referencedColumnName = "id")
    private farmaceuticoEntity farmaceutico;

    @Column(name = "fecha_retiro")
    private LocalDate fechaRetiro;

    @Column(name = "activo")
    private Boolean activo;
}
