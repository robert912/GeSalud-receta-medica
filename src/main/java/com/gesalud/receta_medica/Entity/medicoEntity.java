package com.gesalud.receta_medica.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "medico")
public class medicoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne
    @JoinColumn(name = "id_persona", referencedColumnName = "id")
    private personaEntity persona;

    @Column(name = "especialidad", length = 120)
    private String especialidad;

    @Column(name = "activo")
    private Boolean activo;
}
