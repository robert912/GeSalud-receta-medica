package com.gesalud.receta_medica.Controller;

import com.gesalud.receta_medica.Entity.detalleRecetaEntity;
import com.gesalud.receta_medica.Service.detalleRecetaService;
import com.gesalud.receta_medica.dto.detalleRecetaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/detalle")
@CrossOrigin(origins = "http://localhost:5173")
public class detalleRecetaController {
    private detalleRecetaService detService;

    @Autowired
    public detalleRecetaController(detalleRecetaService detalleService) {
        this.detService = detalleService;
    }

    @GetMapping ("/all")//GET -> /detalle/all
    public ResponseEntity<List<detalleRecetaEntity>> obtenerTodosLosDetalles(){
        List<detalleRecetaEntity> detalle = detService.obtenerDetalleRecetaAll();
        return ResponseEntity.ok(detalle);
    }

    @GetMapping //GET -> /detalle
    public ResponseEntity<List<detalleRecetaEntity>> todosLosDetalleRecetaActivas(){
        List<detalleRecetaEntity> detalle = detService.obtenerDetalleRecetaActivos();
        return ResponseEntity.ok(detalle);
    }

    @PostMapping
    public ResponseEntity<?> crearReceta(@RequestBody detalleRecetaDTO nuevoDetalle) {
        detalleRecetaEntity detalleCreado = detService.guardarDetalleRecetaDTO(nuevoDetalle);
        return new ResponseEntity<>(detalleCreado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}") // PUT -> /detalle/{id}
    public ResponseEntity<detalleRecetaEntity> actualizarDetalleReceta(@PathVariable Long id, @RequestBody detalleRecetaEntity detalleActualizado) {
        Optional<detalleRecetaEntity> detalleExistente = detService.obtenerDetalleRecetaById(id);

        if (detalleExistente.isPresent()) {
            detalleRecetaEntity detalle = detalleExistente.get();

            if (detalleActualizado.getReceta() != null) {
                detalle.setReceta(detalleActualizado.getReceta());
            }

            if (detalleActualizado.getMedicamento() != null) {
                detalle.setMedicamento(detalleActualizado.getMedicamento());
            }

            if (detalleActualizado.getConcentracion() != null && !detalleActualizado.getConcentracion().isBlank()) {
                detalle.setConcentracion(detalleActualizado.getConcentracion());
            }

            if (detalleActualizado.getDosis() != null && !detalleActualizado.getDosis().isBlank()) {
                detalle.setDosis(detalleActualizado.getDosis());
            }

            if (detalleActualizado.getFrecuencia() != null && !detalleActualizado.getFrecuencia().isBlank()) {
                detalle.setFrecuencia(detalleActualizado.getFrecuencia());
            }

            if (detalleActualizado.getDuracion() != null && !detalleActualizado.getDuracion().isBlank()) {
                detalle.setDuracion(detalleActualizado.getDuracion());
            }

            if (detalleActualizado.getActivo() != null) {
                detalle.setActivo(detalleActualizado.getActivo());
            }

            return new ResponseEntity<>(detService.guardarDetalle(detalle), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/desactivar/{id}") // PUT -> /detalle/desactivar/{id} //Cambiar estado del detalle receta a desactivado
    public ResponseEntity<detalleRecetaEntity> desactivarDetalle(@PathVariable Long id) {
        Optional<detalleRecetaEntity> detalleExistente = detService.buscarDetalleRecetaPorId(id);

        if (detalleExistente.isPresent()) {
            detalleRecetaEntity detalle = detalleExistente.get();
            detalle.setActivo(false);
            detalleRecetaEntity detalleActualizada = detService.guardarDetalle(detalle);
            return new ResponseEntity<>(detalleActualizada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/receta/{id}")// GET -> /detalle/receta/{id}
    public ResponseEntity<?> obtenerDetalleRecetasActivasPorReceta(@PathVariable Long id) {
        List<detalleRecetaEntity> detalle = detService.obtenerDetalleRecetasActivasPorReceta(id);
        if (detalle.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontraron detalles para receta: " + id);
        }
        return ResponseEntity.ok(detalle);
    }
}

