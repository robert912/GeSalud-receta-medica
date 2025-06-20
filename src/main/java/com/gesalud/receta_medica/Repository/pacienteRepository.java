package com.gesalud.receta_medica.Repository;

import com.gesalud.receta_medica.Entity.pacienteEntity;
import com.gesalud.receta_medica.Entity.personaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface pacienteRepository extends JpaRepository<pacienteEntity, Long> {
    // Metodo para listar solo personas activas
    List<pacienteEntity> findByActivoTrue();

    // Para búsquedas individuales por ID, si necesitas validar estado activo
    Optional<pacienteEntity> findByIdAndActivoTrue(Long id);

    Optional<pacienteEntity> findByPersona(personaEntity persona);
}
