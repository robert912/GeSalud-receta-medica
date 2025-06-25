package com.gesalud.receta_medica.Controller;

import com.gesalud.receta_medica.Entity.recetaEntity;
import com.gesalud.receta_medica.Service.recetaService;
import com.gesalud.receta_medica.constants.AppConstants;
import com.gesalud.receta_medica.dto.recetaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/receta")
@CrossOrigin(origins = AppConstants.FRONTEND_URL)
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

    @GetMapping("/medico/{id_medico}") //GET -> /receta/medico/1
    public ResponseEntity<List<recetaEntity>> todasLasRecetaActivasPorMedico(@PathVariable Long id_medico){
        List<recetaEntity> receta = recService.obtenerRecetaActivosPorMedico(id_medico);
        return ResponseEntity.ok(receta);
    }


    @GetMapping("/{rut}")
    public ResponseEntity<?> obtenerRecetasActivasPorRut(@PathVariable String rut) {
        List<recetaEntity> recetas = recService.obtenerRecetasActivasPorRutPaciente(rut);
        if (recetas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontraron recetas activas para el paciente con RUT: " + rut);
        }
        return ResponseEntity.ok(recetas);
    }

    @PostMapping
    public ResponseEntity<?> crearReceta(@RequestBody recetaDTO nuevaReceta) {
        recetaEntity recetaCreada = recService.guardarRecetaDTO(nuevaReceta);
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

    @PutMapping("/bloquear/{id}") // PUT -> /receta/bloquear/{id} //Cambiar estado de receta a no disponible
    public ResponseEntity<recetaEntity> bloquearReceta(@PathVariable Long id) {
        Optional<recetaEntity> recetaExistente = recService.buscarRecetaPorId(id);

        if (recetaExistente.isPresent()) {
            recetaEntity receta = recetaExistente.get();
            receta.setDisponible(false);
            recetaEntity recetaActualizada = recService.guardarReceta(receta);
            return new ResponseEntity<>(recetaActualizada, HttpStatus.OK);
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

