package com.gesalud.receta_medica.Repository;

import com.gesalud.receta_medica.Entity.personaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface personaRepository extends JpaRepository<personaEntity, Long> {
    // Para búsquedas individuales por Rut
    Optional<personaEntity> findByRut(String rut);

    // Metodo para listar solo personas activas
    List<personaEntity> findByActivoTrue();

    // Para búsquedas individuales por ID, si necesitas validar estado activo
    Optional<personaEntity> findByIdAndActivoTrue(Long id);
}
