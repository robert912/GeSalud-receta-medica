package com.gesalud.receta_medica.Repository;

import com.gesalud.receta_medica.Entity.medicamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface medicamentoRepository extends JpaRepository<medicamentoEntity, Long> {
    // Metodo para listar solo medicamentos activos
    List<medicamentoEntity> findByActivoTrue();

    // Para búsquedas individuales por ID, si necesitas validar estado activo
    Optional<medicamentoEntity> findByIdAndActivoTrue(Long id);

    Optional<medicamentoEntity> findByNombre(String medicamento);
}
