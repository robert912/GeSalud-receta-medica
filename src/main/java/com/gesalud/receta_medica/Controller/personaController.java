package com.gesalud.receta_medica.Controller;

import com.gesalud.receta_medica.Entity.personaEntity;
import com.gesalud.receta_medica.Service.personaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/personas")
public class personaController {
    private personaService perService;

    @Autowired
    public personaController(personaService perService) {
        this.perService = perService;
    }

    @GetMapping //GET -> /api/personas
    public ResponseEntity<List<personaEntity>> obtenerTodasLasPersonas(){
        List<personaEntity> personas = perService.obtenerPersonaAll();
        return ResponseEntity.ok(personas);//new
    }

    @GetMapping("/{rut}") //GET -> /api/personas/17411947-3
    public ResponseEntity<personaEntity> obtenerPersonaPorRut(@PathVariable String rut){
        return perService.obtenerPersonaByRut(rut)
                .map(persona -> new ResponseEntity<>(persona, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

