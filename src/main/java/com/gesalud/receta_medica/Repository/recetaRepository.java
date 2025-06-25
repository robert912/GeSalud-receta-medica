package com.gesalud.receta_medica.Repository;

import com.gesalud.receta_medica.Entity.recetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface recetaRepository extends JpaRepository<recetaEntity, Long> {
    // Metodo para listar solo recetas activas
    List<recetaEntity> findByActivoTrueOrderByFechaCreacionDesc();

    // Solo receta activos por medico y fecha de creacion Desc
    List<recetaEntity> findByActivoTrueAndMedicoIdOrderByFechaCreacionDesc(Long idMedico);

    // Para búsquedas individuales por ID y estado activo
    Optional<recetaEntity> findByIdAndActivoTrue(Long id);

    // Para búsquedas individuales por ID y disponible
    Optional<recetaEntity> findByIdAndDisponibleTrue(Long id);

    // Buscar receta por rut disponible, disponible y activo
    List<recetaEntity> findByDisponibleTrueAndActivoTrueAndPacientePersonaRut(String rut);
}
