package com.gesalud.receta_medica.Controller;

import com.gesalud.receta_medica.Entity.medicamentoEntity;
import com.gesalud.receta_medica.Service.medicamentoService;
import com.gesalud.receta_medica.constants.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medicamento")
@CrossOrigin(origins = AppConstants.FRONTEND_URL)
public class medicamentoController {
    private medicamentoService medService;

    @Autowired
    public medicamentoController(medicamentoService medService) {
        this.medService = medService;
    }

    @GetMapping ("/all")//GET -> /medicamento/all
    public ResponseEntity<List<medicamentoEntity>> obtenerTodosLosMedicamento(){
        List<medicamentoEntity> medicamento = medService.obtenerMedicamentoAll();
        return ResponseEntity.ok(medicamento);
    }

    @GetMapping //GET -> /medicamento
    public ResponseEntity<List<medicamentoEntity>> todosLosMedicamentoActivas(){
        List<medicamentoEntity> medicamento = medService.obtenerMedicamentoActivos();
        return ResponseEntity.ok(medicamento);
    }

    @GetMapping("/{nombre}") //GET -> /medicamento/ibuprofeno
    public ResponseEntity<medicamentoEntity> obtenerMedicamentoPorNombre(@PathVariable String nombre){
        return medService.obtenerMedicamentoByNombre(nombre)
                .map(medicamento -> new ResponseEntity<>(medicamento, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/id/{id}") //GET -> /medicamento/1
    public ResponseEntity<medicamentoEntity> obtenerMedicamentoPorId(@PathVariable Long id){
        return medService.buscarPorId(id)
                .map(medicamento -> new ResponseEntity<>(medicamento, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> crearMedicamento(@RequestBody medicamentoEntity nuevoMedicamento) {
        nuevoMedicamento.setActivo(true);
        medicamentoEntity medicamentoCreado = medService.guardarMedicamento(nuevoMedicamento);
        return new ResponseEntity<>(medicamentoCreado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}") // PUT -> /medicamento/{id}
    public ResponseEntity<medicamentoEntity> actualizarMedicamento(@PathVariable Long id, @RequestBody medicamentoEntity medicamentoActualizado) {
        Optional<medicamentoEntity> medicamentoExistente = medService.obtenerMedicamentoById(id);
        if (medicamentoExistente.isPresent()) {
            medicamentoEntity medicamento = medicamentoExistente.get();

            if (medicamentoActualizado.getNombre() != null && !medicamentoActualizado.getNombre().isBlank()) {
                medicamento.setNombre(medicamentoActualizado.getNombre());
            }
            if (medicamentoActualizado.getUso() != null && !medicamentoActualizado.getUso().isBlank()) {
                medicamento.setUso(medicamentoActualizado.getUso());
            }
            if (medicamentoActualizado.getControlado() != null) {
                medicamento.setControlado(medicamentoActualizado.getControlado());
            }
            if (medicamentoActualizado.getConcentracion() != null && !medicamentoActualizado.getConcentracion().isBlank()) {
                medicamento.setConcentracion(medicamentoActualizado.getConcentracion());
            }
            if (medicamentoActualizado.getPresentacion() != null && !medicamentoActualizado.getPresentacion().isBlank()) {
                medicamento.setPresentacion(medicamentoActualizado.getPresentacion());
            }
            if (medicamentoActualizado.getDosis() != null && !medicamentoActualizado.getDosis().isBlank()) {
                medicamento.setDosis(medicamentoActualizado.getDosis());
            }
            if (medicamentoActualizado.getFrecuencia() != null && !medicamentoActualizado.getFrecuencia().isBlank()) {
                medicamento.setFrecuencia(medicamentoActualizado.getFrecuencia());
            }
            if (medicamentoActualizado.getDuracion() != null && !medicamentoActualizado.getDuracion().isBlank()) {
                medicamento.setDuracion(medicamentoActualizado.getDuracion());
            }
            if (medicamentoActualizado.getIndicaciones() != null && !medicamentoActualizado.getIndicaciones().isBlank()) {
                medicamento.setIndicaciones(medicamentoActualizado.getIndicaciones());
            }
            if (medicamentoActualizado.getStock() >= 0) {
                medicamento.setStock(medicamentoActualizado.getStock());
            }
            if (medicamentoActualizado.getValor() >= 0) {
                medicamento.setValor(medicamentoActualizado.getValor());
            }
            if (medicamentoActualizado.getActivo() != null) {
                medicamento.setActivo(medicamentoActualizado.getActivo());
            }

            return new ResponseEntity<>(medService.guardarMedicamento(medicamento), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}") // DELETE lógico -> /medicamento/{id}
    public ResponseEntity<Void> eliminarMedicamentoLogicamente(@PathVariable Long id) {
        Optional<medicamentoEntity> medicamentoExistente = medService.medicamentoByIdActivo(id);
        if (medicamentoExistente.isPresent()) {
            medicamentoEntity medicamento = medicamentoExistente.get();
            medicamento.setActivo(false);
            medService.guardarMedicamento(medicamento);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

