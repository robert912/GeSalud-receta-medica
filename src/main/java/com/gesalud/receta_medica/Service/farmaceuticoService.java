package com.gesalud.receta_medica.Service;

import com.gesalud.receta_medica.Entity.farmaceuticoEntity;
import com.gesalud.receta_medica.Entity.personaEntity;
import com.gesalud.receta_medica.Repository.farmaceuticoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class farmaceuticoService {
    private farmaceuticoRepository farmaceuticoRepository;
    private personaService personaService;

    @Autowired
    public farmaceuticoService(farmaceuticoRepository farmaceuticoRepo, personaService personaServ) {
        this.personaService = personaServ;
        this.farmaceuticoRepository = farmaceuticoRepo;
    }

    public farmaceuticoEntity guardarFarmaceutico(farmaceuticoEntity farmaceutico) {
        return farmaceuticoRepository.save(farmaceutico);
    }

    public List<farmaceuticoEntity> obtenerFarmaceuticoAll() {
        return farmaceuticoRepository.findAll();
    }

    // Solo Farmaceutico activos
    public List<farmaceuticoEntity> obtenerFarmaceuticoActivos() {
        return farmaceuticoRepository.findByActivoTrue();
    }

    public Optional<farmaceuticoEntity> obtenerFarmaceuticoById(Long id) {
        return farmaceuticoRepository.findById(id);
    }

    // Para PUT y DELETE lógico por ID
    public Optional<farmaceuticoEntity> farmaceuticoByIdActivo(Long id) {
        return farmaceuticoRepository.findByIdAndActivoTrue(id);
    }

    public ResponseEntity<?> crearFarmaceuticoConValidacion(farmaceuticoEntity nuevoFarmaceutico) {
        String rut = nuevoFarmaceutico.getPersona().getRut();

        Optional<personaEntity> personaExistenteOpt = personaService.obtenerPersonaByRut(rut);

        if (personaExistenteOpt.isPresent()) {
            personaEntity personaExistente = personaExistenteOpt.get();

            Optional<farmaceuticoEntity> farmaceuticoExistenteOpt = farmaceuticoRepository.findByPersona(personaExistente);
            if (farmaceuticoExistenteOpt.isPresent()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("Ya existe un farmaceutico registrado para la persona con RUT " + rut);
            }

            // Si persona existe y no hay farmaceutico, usar la persona existente
            nuevoFarmaceutico.setPersona(personaExistente);
        } else {
            // Si la persona no existe, guardar primero persona
            personaEntity nuevaPersona = personaService.guardarPersona(nuevoFarmaceutico.getPersona());
            nuevoFarmaceutico.setPersona(nuevaPersona);
        }

        nuevoFarmaceutico.setActivo(true);
        farmaceuticoEntity farmaceuticoCreado = farmaceuticoRepository.save(nuevoFarmaceutico);

        return ResponseEntity.status(HttpStatus.CREATED).body(farmaceuticoCreado);
    }
}
