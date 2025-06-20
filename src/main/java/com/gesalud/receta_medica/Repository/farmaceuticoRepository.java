package com.gesalud.receta_medica.Repository;

import com.gesalud.receta_medica.Entity.farmaceuticoEntity;
import com.gesalud.receta_medica.Entity.personaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface farmaceuticoRepository extends JpaRepository<farmaceuticoEntity, Long> {
    // Metodo para listar solo farmaceuticos activos
    List<farmaceuticoEntity> findByActivoTrue();

    // Para búsquedas individuales por ID, si necesitas validar estado activo
    Optional<farmaceuticoEntity> findByIdAndActivoTrue(Long id);

    Optional<farmaceuticoEntity> findByPersona(personaEntity persona);
}
