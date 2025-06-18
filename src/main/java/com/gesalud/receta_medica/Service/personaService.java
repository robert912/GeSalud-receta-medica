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

    public List<personaEntity> obtenerPersonaAll() {
        return personaRepository.findAll();
    }

    public Optional<personaEntity> obtenerPersonaByRut(String rut) {
        return personaRepository.findByRut(rut);
    }
}
