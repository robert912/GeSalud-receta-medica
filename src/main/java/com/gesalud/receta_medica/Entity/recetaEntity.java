package com.gesalud.receta_medica.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "receta")
public class recetaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_paciente", referencedColumnName = "id")
    private pacienteEntity paciente;

    @ManyToOne
    @JoinColumn(name = "id_medico", referencedColumnName = "id")
    private medicoEntity medico;

    @Column(name = "disponible")
    private Boolean disponible;

    @Column(name = "fecha_emision")
    private LocalDate fechaCreacion;

    @Column(name = "activo")
    private Boolean activo;
}
