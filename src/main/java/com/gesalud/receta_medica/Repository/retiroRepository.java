package com.gesalud.receta_medica.Repository;

import com.gesalud.receta_medica.Entity.retiroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface retiroRepository extends JpaRepository<retiroEntity, Long> {
    // Metodo para listar retiros
    List<retiroEntity> findByActivoTrue();

    // Para búsquedas individuales por ID, si necesitas validar estado activo
    Optional<retiroEntity> findByIdAndActivoTrue(Long id);
}
