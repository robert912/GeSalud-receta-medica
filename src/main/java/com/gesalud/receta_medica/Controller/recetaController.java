package com.gesalud.receta_medica.Controller;

import com.gesalud.receta_medica.Entity.recetaEntity;
import com.gesalud.receta_medica.Service.recetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/receta")
public class recetaController {
    private recetaService recService;

    @Autowired
    public recetaController(recetaService recService) {
        this.recService = recService;
    }

    @GetMapping ("/all")//GET -> /receta/all
    public ResponseEntity<List<recetaEntity>> obtenerTodasLasRecetas(){
        List<recetaEntity> receta = recService.obtenerRecetaAll();
        return ResponseEntity.ok(receta);
    }

    @GetMapping //GET -> /receta
    public ResponseEntity<List<recetaEntity>> todosLosRecetaActivas(){
        List<recetaEntity> receta = recService.obtenerRecetaActivos();
        return ResponseEntity.ok(receta);
    }

    @PostMapping
    public ResponseEntity<?> crearReceta(@RequestBody recetaEntity nuevaReceta) {
        nuevaReceta.setFechaCreacion(LocalDate.now());
        nuevaReceta.setDisponible(true);
        nuevaReceta.setActivo(true);
        recetaEntity recetaCreada = recService.guardarReceta(nuevaReceta);
        return new ResponseEntity<>(recetaCreada, HttpStatus.CREATED);
    }

    @PutMapping("/{id}") // PUT -> /receta/{id}
    public ResponseEntity<recetaEntity> actualizarReceta(@PathVariable Long id, @RequestBody recetaEntity recetaActualizada) {
        Optional<recetaEntity> recetaExistente = recService.obtenerRecetaById(id);

        if (recetaExistente.isPresent()) {
            recetaEntity receta = recetaExistente.get();

            if (recetaActualizada.getPaciente() != null) {
                receta.setPaciente(recetaActualizada.getPaciente());
            }

            if (recetaActualizada.getMedico() != null) {
                receta.setMedico(recetaActualizada.getMedico());
            }

            if (recetaActualizada.getDisponible() != null) {
                receta.setDisponible(recetaActualizada.getDisponible());
            }

            if (recetaActualizada.getActivo() != null) {
                receta.setActivo(recetaActualizada.getActivo());
            }

            return new ResponseEntity<>(recService.guardarReceta(receta), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}") // DELETE lógico -> /receta/{id}
    public ResponseEntity<Void> eliminarRecetaLogicamente(@PathVariable Long id) {
        Optional<recetaEntity> recetaExistente = recService.recetaByIdActivo(id);
        if (recetaExistente.isPresent()) {
            recetaEntity receta = recetaExistente.get();
            receta.setActivo(false);
            recService.guardarReceta(receta);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

