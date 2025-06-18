package com.gesalud.receta_medica.Repository;

import com.gesalud.receta_medica.Entity.pacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface pacienteRepository extends JpaRepository<pacienteEntity, Integer> {
}
