package com.gesalud.receta_medica.Controller;

import com.gesalud.receta_medica.Entity.personaEntity;
import com.gesalud.receta_medica.Service.personaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/persona")
public class personaController {
    private personaService perService;

    @Autowired
    public personaController(personaService perService) {
        this.perService = perService;
    }

    @GetMapping ("/all")//GET -> /api/persona/all
    public ResponseEntity<List<personaEntity>> obtenerTodasLasPersonas(){
        List<personaEntity> personas = perService.obtenerPersonaAll();
        return ResponseEntity.ok(personas);//new
    }

    @GetMapping //GET -> /api/persona
    public ResponseEntity<List<personaEntity>> todasLasPersonasActivas(){
        List<personaEntity> personas = perService.obtenerPersonasActivas();
        return ResponseEntity.ok(personas);//new
    }


    @GetMapping("/{rut}") //GET -> /api/persona/17411947-3
    public ResponseEntity<personaEntity> obtenerPersonaPorRut(@PathVariable String rut){
        return perService.obtenerPersonaByRut(rut)
                .map(persona -> new ResponseEntity<>(persona, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // POST -> /api/persona
    @PostMapping
    public ResponseEntity<personaEntity> crearPersona(@RequestBody personaEntity nuevaPersona) {
        nuevaPersona.setActivo(true); // Activo por defecto
        personaEntity personaCreada = perService.guardarPersona(nuevaPersona);
        return new ResponseEntity<>(personaCreada, HttpStatus.CREATED);
    }

    // PUT -> /api/persona/{id}
    @PutMapping("/{id}")
    public ResponseEntity<personaEntity> actualizarPersona(@PathVariable Long id, @RequestBody personaEntity personaActualizada) {
        Optional<personaEntity> personaExistente = perService.obtenerPersonaById(id);
        if (personaExistente.isPresent()) {
            personaEntity persona = personaExistente.get();

            if (personaActualizada.getRut() != null && !personaActualizada.getRut().isBlank()) {
                persona.setRut(personaActualizada.getRut());
            }
            if (personaActualizada.getNombre() != null && !personaActualizada.getNombre().isBlank()) {
                persona.setNombre(personaActualizada.getNombre());
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

            return new ResponseEntity<>(perService.guardarPersona(persona), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE lógico -> /api/persona/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPersonaLogicamente(@PathVariable Long id) {
        Optional<personaEntity> personaExistente = perService.obtenerPersonaById(id);
        if (personaExistente.isPresent()) {
            personaEntity persona = personaExistente.get();
            persona.setActivo(false);
            perService.guardarPersona(persona);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

