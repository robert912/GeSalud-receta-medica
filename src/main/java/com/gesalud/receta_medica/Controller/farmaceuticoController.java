package com.gesalud.receta_medica.Controller;

import com.gesalud.receta_medica.Entity.farmaceuticoEntity;
import com.gesalud.receta_medica.Service.farmaceuticoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/farmaceutico")
@CrossOrigin(origins = "http://localhost:5173")
public class farmaceuticoController {
    private farmaceuticoService farService;

    @Autowired
    public farmaceuticoController(farmaceuticoService farService) {
        this.farService = farService;
    }

    @GetMapping("/all")//GET -> /farmaceutico/all
    public ResponseEntity<List<farmaceuticoEntity>> obtenerTodosLosFarmaceuticos(){
        List<farmaceuticoEntity> farmaceutico = farService.obtenerFarmaceuticoAll();
        return ResponseEntity.ok(farmaceutico);//new
    }

    @GetMapping //GET -> /farmaceutico
    public ResponseEntity<List<farmaceuticoEntity>> todasLosfarmaceuticoActivos(){
        List<farmaceuticoEntity> farmaceutico = farService.obtenerFarmaceuticoActivos();
        return ResponseEntity.ok(farmaceutico);//new
    }

    @PostMapping // POST -> /farmaceutico
    public ResponseEntity<?> crearFarmaceutico(@RequestBody farmaceuticoEntity nuevoFarmaceutico) {
        return farService.crearFarmaceuticoConValidacion(nuevoFarmaceutico);
    }

    @PutMapping("/{id}") // PUT -> /farmaceutico/{id}
    public ResponseEntity<farmaceuticoEntity> actualizarFarmaceutico(@PathVariable Long id, @RequestBody farmaceuticoEntity farmaceuticoActualizada) {
        Optional<farmaceuticoEntity> farmaceuticoExistente = farService.obtenerFarmaceuticoById(id);
        if (farmaceuticoExistente.isPresent()) {
            farmaceuticoEntity farmaceutico = farmaceuticoExistente.get();

            if (farmaceuticoActualizada.getSucursal() != null && !farmaceuticoActualizada.getSucursal().isBlank()) {
                farmaceutico.setSucursal(farmaceuticoActualizada.getSucursal());
            }

            if (farmaceuticoActualizada.getActivo() != null) {
                farmaceutico.setActivo(farmaceuticoActualizada.getActivo());
            }

            return new ResponseEntity<>(farService.guardarFarmaceutico(farmaceutico), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}") // DELETE lógico -> /farmaceutico/{id}
    public ResponseEntity<Void> eliminarFarmaceuticoLogicamente(@PathVariable Long id) {
        Optional<farmaceuticoEntity> farmaceuticoExistente = farService.farmaceuticoByIdActivo(id);
        if (farmaceuticoExistente.isPresent()) {
            farmaceuticoEntity farmaceutico = farmaceuticoExistente.get();
            farmaceutico.setActivo(false);
            farService.guardarFarmaceutico(farmaceutico);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
