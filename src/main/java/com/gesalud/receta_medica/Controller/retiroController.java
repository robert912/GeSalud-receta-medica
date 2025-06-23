package com.gesalud.receta_medica.Controller;

import com.gesalud.receta_medica.Entity.retiroEntity;
import com.gesalud.receta_medica.Service.retiroService;
import com.gesalud.receta_medica.dto.retiroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/retiro")
@CrossOrigin(origins = "http://localhost:5173")
public class retiroController {
    private retiroService retService;

    @Autowired
    public retiroController(retiroService retService) {
        this.retService = retService;
    }

    @GetMapping ("/all")//GET -> /retiro/all
    public ResponseEntity<List<retiroEntity>> obtenerTodasLasRecetas(){
        List<retiroEntity> retiro = retService.obtenerRetiroAll();
        return ResponseEntity.ok(retiro);
    }

    @GetMapping //GET -> /retiro
    public ResponseEntity<List<retiroEntity>> todosLosRetirosActivos(){
        List<retiroEntity> retiro = retService.obtenerRetiroActivos();
        return ResponseEntity.ok(retiro);
    }

    @PostMapping
    public ResponseEntity<?> crearRetiro(@RequestBody retiroDTO nuevaRetiro) {
        retiroEntity retiroCreada = retService.guardarRetiroDTO(nuevaRetiro);
        return new ResponseEntity<>(retiroCreada, HttpStatus.CREATED);
    }

    @PutMapping("/{id}") // PUT -> /retiro/{id}
    public ResponseEntity<retiroEntity> actualizarRetiro(@PathVariable Long id, @RequestBody retiroEntity retiroActualizado) {
        Optional<retiroEntity> retiroExistente = retService.obtenerRetiroById(id);

        if (retiroExistente.isPresent()) {
            retiroEntity retiro = retiroExistente.get();

            if (retiroActualizado.getDetalleReceta() != null) {
                retiro.setDetalleReceta(retiroActualizado.getDetalleReceta());
            }

            if (retiroActualizado.getFarmaceutico() != null) {
                retiro.setFarmaceutico(retiroActualizado.getFarmaceutico());
            }

            if (retiroActualizado.getActivo() != null) {
                retiro.setActivo(retiroActualizado.getActivo());
            }

            return new ResponseEntity<>(retService.guardarRetiro(retiro), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}") // DELETE lógico -> /retiro/{id}
    public ResponseEntity<Void> eliminarRetiroLogicamente(@PathVariable Long id) {
        Optional<retiroEntity> retiroExistente = retService.retiroByIdActivo(id);
        if (retiroExistente.isPresent()) {
            retiroEntity retiro = retiroExistente.get();
            retiro.setActivo(false);
            retService.guardarRetiro(retiro);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

