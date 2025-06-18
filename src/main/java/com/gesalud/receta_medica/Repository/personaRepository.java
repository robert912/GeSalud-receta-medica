package com.gesalud.receta_medica.Repository;

import com.gesalud.receta_medica.Entity.personaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface personaRepository extends JpaRepository<personaEntity, Long> {
    Optional<personaEntity> findByRut(String rut);
}
