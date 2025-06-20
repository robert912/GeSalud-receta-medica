package com.gesalud.receta_medica.Service;

import com.gesalud.receta_medica.Entity.medicoEntity;
import com.gesalud.receta_medica.Entity.personaEntity;
import com.gesalud.receta_medica.Repository.medicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class medicoService {
    private medicoRepository medicoRepository;
    private personaService personaService;

    @Autowired
    public medicoService(medicoRepository medicoRepo,  personaService personaServ) {
        this.personaService = personaServ;
        this.medicoRepository = medicoRepo;
    }

    public medicoEntity guardarMedico(medicoEntity medico) {
        return medicoRepository.save(medico);
    }

    public List<medicoEntity> obtenerMedicoAll() {
        return medicoRepository.findAll();
    }

    public List<medicoEntity> obtenerMedicoActivos() { // Solo medico activos
        return medicoRepository.findByActivoTrue();
    }

    public Optional<medicoEntity> obtenerMedicoById(Long id) {
        return medicoRepository.findById(id);
    }

    public Optional<medicoEntity> medicoByIdActivo(Long id) { // Para PUT y DELETE lógico por ID
        return medicoRepository.findByIdAndActivoTrue(id);
    }

    public ResponseEntity<?> crearMedicoConValidacion(medicoEntity nuevoMedico) {
        String rut = nuevoMedico.getPersona().getRut();

        Optional<personaEntity> personaExistenteOpt = personaService.obtenerPersonaByRut(rut);

        if (personaExistenteOpt.isPresent()) {
            personaEntity personaExistente = personaExistenteOpt.get();

            Optional<medicoEntity> medicoExistenteOpt = medicoRepository.findByPersona(personaExistente);
            if (medicoExistenteOpt.isPresent()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("Ya existe un medico registrado para la persona con RUT " + rut);
            }

            // Si persona existe y no hay medico, usar la persona existente
            nuevoMedico.setPersona(personaExistente);
        } else {
            // Si la persona no existe, guardar primero persona
            personaEntity nuevaPersona = personaService.guardarPersona(nuevoMedico.getPersona());
            nuevoMedico.setPersona(nuevaPersona);
        }

        nuevoMedico.setActivo(true);
        medicoEntity medicoCreado = medicoRepository.save(nuevoMedico);

        return ResponseEntity.status(HttpStatus.CREATED).body(medicoCreado);
    }
}
