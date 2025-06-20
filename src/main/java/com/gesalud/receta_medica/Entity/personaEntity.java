package com.gesalud.receta_medica.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "persona")
public class personaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "rut",  length = 11)
    private  String rut;

    @Column(name = "nombre", length = 80)
    private String nombre;

    @Column(name = "apellido", length = 50)
    private String apellido;

    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @Column(name = "telefono")
    private long telefono;

    @Column(name = "correo",  length = 50)
    private  String correo;

    @Column(name = "activo")
    private Boolean activo;
}
