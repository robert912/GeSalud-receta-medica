package com.gesalud.receta_medica.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "farmaceutico")
public class farmaceuticoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne
    @JoinColumn(name = "id_persona", referencedColumnName = "id")
    private personaEntity persona;

    @Column(name = "sucursal", length = 80)
    private String sucursal;

    @Column(name = "activo")
    private Boolean activo;
}
