package com.gesalud.receta_medica.Repository;

import com.gesalud.receta_medica.Entity.detalleRecetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface detalleRecetaRepository extends JpaRepository<detalleRecetaEntity, Long> {
    // Metodo para listar solo medicamentos activos
    List<detalleRecetaEntity> findByActivoTrue();

    // Para búsquedas individuales por ID, si necesitas validar estado activo
    Optional<detalleRecetaEntity> findByIdAndActivoTrue(Long id);

    // Buscar receta por rut disponible
    List<detalleRecetaEntity> findByActivoTrueAndRecetaId(Long id);
}
