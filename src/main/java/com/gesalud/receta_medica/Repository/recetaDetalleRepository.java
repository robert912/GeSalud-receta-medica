package com.gesalud.receta_medica.Repository;

import com.gesalud.receta_medica.Entity.recetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface recetaDetalleRepository extends JpaRepository<recetaEntity, Long> {
    // Metodo para listar solo medicamentos activos
    List<recetaEntity> findByActivoTrue();

    // Para búsquedas individuales por ID, si necesitas validar estado activo
    Optional<recetaEntity> findByIdAndActivoTrue(Long id);
}
