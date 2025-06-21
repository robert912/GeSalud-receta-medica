package com.gesalud.receta_medica.Service;

import com.gesalud.receta_medica.Entity.pacienteEntity;
import com.gesalud.receta_medica.Entity.personaEntity;
import com.gesalud.receta_medica.Repository.pacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class pacienteService {
    private pacienteRepository pacienteRepository;
    private personaService personaService;

    @Autowired
    public pacienteService(pacienteRepository pacienteRepo, personaService personaServ) {
        this.personaService = personaServ;
        this.pacienteRepository = pacienteRepo;
    }

    public pacienteEntity guardarPaciente(pacienteEntity paciente) {
        return pacienteRepository.save(paciente);
    }

    public List<pacienteEntity> obtenerPacienteAll() {
        return pacienteRepository.findAll();
    }

    public List<pacienteEntity> obtenerPacienteActivas() { // Solo paciente activos
        return pacienteRepository.findByActivoTrue();
    }

    public Optional<pacienteEntity> obtenerPacienteById(Long id) {
        return pacienteRepository.findById(id);
    }

    public Optional<pacienteEntity> pacienteByIdActivo(Long id) { // Para PUT y DELETE lógico por ID
        return pacienteRepository.findByIdAndActivoTrue(id);
    }

    public ResponseEntity<?> crearPacienteConValidacion(pacienteEntity nuevoPaciente) {
        String rut = nuevoPaciente.getPersona().getRut();

        Optional<personaEntity> personaExistenteOpt = personaService.obtenerPersonaByRut(rut);

        if (personaExistenteOpt.isPresent()) {
            personaEntity personaExistente = personaExistenteOpt.get();

            Optional<pacienteEntity> pacienteExistenteOpt = pacienteRepository.findByPersona(personaExistente);
            if (pacienteExistenteOpt.isPresent()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("Ya existe un paciente registrado para la persona con RUT " + rut);
            }

            // Si persona existe y no hay paciente, usar la persona existente
            nuevoPaciente.setPersona(personaExistente);
        } else {
            // Si la persona no existe, guardar primero persona
            personaEntity nuevaPersona = personaService.guardarPersona(nuevoPaciente.getPersona());
            nuevoPaciente.setPersona(nuevaPersona);
        }

        nuevoPaciente.setActivo(true);
        pacienteEntity pacienteCreado = pacienteRepository.save(nuevoPaciente);

        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteCreado);
    }

    public Optional<pacienteEntity> buscarPorId(Long id) {
        return pacienteRepository.findById(id);
    }
}
