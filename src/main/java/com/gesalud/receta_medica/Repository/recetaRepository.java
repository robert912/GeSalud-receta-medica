package com.gesalud.receta_medica.Repository;

import com.gesalud.receta_medica.Entity.recetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface recetaRepository extends JpaRepository<recetaEntity, Long> {
    // Metodo para listar solo recetas activas
    List<recetaEntity> findByActivoTrueOrderByFechaCreacionDesc();

    // Para búsquedas individuales por ID, si necesitas validar estado activo
    Optional<recetaEntity> findByIdAndActivoTrue(Long id);

    // Para búsquedas individuales por ID, si necesitas validar estado activo
    Optional<recetaEntity> findByIdAndDisponibleTrue(Long id);

    // Buscar receta por rut disponible
    List<recetaEntity> findByDisponibleTrueAndActivoTrueAndPacientePersonaRut(String rut);
}
