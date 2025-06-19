package com.gesalud.receta_medica.Service;

import com.gesalud.receta_medica.Entity.personaEntity;
import com.gesalud.receta_medica.Repository.personaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class personaService {
    private personaRepository personaRepository;

    @Autowired
    public personaService(personaRepository personaRepo) {
        this.personaRepository = personaRepo;
    }

    public personaEntity guardarPersona(personaEntity persona) {
        return personaRepository.save(persona);
    }
    // Todas las personas activas e inactivas
    public List<personaEntity> obtenerPersonaAll() {
        return personaRepository.findAll();
    }

    // Solo personas activas
    public List<personaEntity> obtenerPersonasActivas() {
        return personaRepository.findByActivoTrue();
    }

    // Persona por rut
    public Optional<personaEntity> obtenerPersonaByRut(String rut) {
        return personaRepository.findByRut(rut)
                .filter(persona -> persona.isActivo()); // filtrar solo activos
    }

    // Para PUT y DELETE lógico por ID
    public Optional<personaEntity> obtenerPersonaById(Long id) {
        return personaRepository.findById(id);
    }
}
