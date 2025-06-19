package com.gesalud.receta_medica.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "paciente")
public class pacienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_persona", referencedColumnName = "id")
    private personaEntity persona;

    @Column(name = "prevision", length = 80)
    private String prevision;

    @Column(name = "activo")
    private boolean activo;
}
