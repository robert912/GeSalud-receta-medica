package com.gesalud.receta_medica.Repository;

import com.gesalud.receta_medica.Entity.medicoEntity;
import com.gesalud.receta_medica.Entity.personaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface medicoRepository extends JpaRepository<medicoEntity, Long> {
    // Metodo para listar solo medicos activos
    List<medicoEntity> findByActivoTrue();

    // Para búsquedas individuales por ID, si necesitas validar estado activo
    Optional<medicoEntity> findByIdAndActivoTrue(Long id);

    Optional<medicoEntity> findByPersona(personaEntity persona);
}
