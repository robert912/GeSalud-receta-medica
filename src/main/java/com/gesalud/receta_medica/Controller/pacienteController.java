package com.gesalud.receta_medica.Controller;

import com.gesalud.receta_medica.Entity.pacienteEntity;
import com.gesalud.receta_medica.Service.pacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")
public class pacienteController {
    private pacienteService pacService;

    @Autowired
    public pacienteController(pacienteService pacService) {
        this.pacService = pacService;
    }

    @GetMapping("/all")//GET -> /paciente/all
    public ResponseEntity<List<pacienteEntity>> obtenerTodosLosPacientes(){
        List<pacienteEntity> paciente = pacService.obtenerpacienteAll();
        return ResponseEntity.ok(paciente);//new
    }

    @GetMapping //GET -> /paciente
    public ResponseEntity<List<pacienteEntity>> todasLosPacientesActivas(){
        List<pacienteEntity> paciente = pacService.obtenerPacienteActivas();
        return ResponseEntity.ok(paciente);//new
    }

    // POST -> /paciente
    @PostMapping
    public ResponseEntity<pacienteEntity> crearPaciente(@RequestBody pacienteEntity nuevoPaciente) {
        nuevoPaciente.setActivo(true); // Activo por defecto
        pacienteEntity pacienteCreado = pacService.guardarPaciente(nuevoPaciente);
        return new ResponseEntity<>(pacienteCreado, HttpStatus.CREATED);
    }

    // PUT -> /paciente/{id}
    @PutMapping("/{id}")
    public ResponseEntity<pacienteEntity> actualizarPaciente(@PathVariable Long id, @RequestBody pacienteEntity pacienteActualizada) {
        Optional<pacienteEntity> pacienteExistente = pacService.obtenerPacienteById(id);
        if (personaExistente.isPresent()) {
            pacienteEntity persona = personaExistente.get();

            if (pacienteActualizada.getRut() != null && !pacienteActualizada.getRut().isBlank()) {
                persona.setRut(pacienteActualizada.getRut());
            }
            if (pacienteActualizada.getNombre() != null && !pacienteActualizada.getNombre().isBlank()) {
                persona.setNombre(pacienteActualizada.getNombre());
            }
            if (personaActualizada.getApellido() != null && !personaActualizada.getApellido().isBlank()) {
                persona.setApellido(personaActualizada.getApellido());
            }
            if (personaActualizada.getFechaNacimiento() != null) {
                persona.setFechaNacimiento(personaActualizada.getFechaNacimiento());
            }
            if (personaActualizada.getTelefono() != 0) { // Validar que no sea 0
                persona.setTelefono(personaActualizada.getTelefono());
            }
            if (personaActualizada.getCorreo() != null && !personaActualizada.getCorreo().isBlank()) {
                persona.setCorreo(personaActualizada.getCorreo());
            }

            persona.setActivo(personaActualizada.isActivo());

            return new ResponseEntity<>(pacService.guardarPersona(persona), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE lógico -> /api/persona/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPersonaLogicamente(@PathVariable Long id) {
        Optional<pacienteEntity> personaExistente = pacService.obtenerPersonaById(id);
        if (personaExistente.isPresent()) {
            pacienteEntity persona = personaExistente.get();
            persona.setActivo(false);
            pacService.guardarPersona(persona);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
