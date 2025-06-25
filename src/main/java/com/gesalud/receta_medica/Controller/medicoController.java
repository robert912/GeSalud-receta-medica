package com.gesalud.receta_medica.Controller;

import com.gesalud.receta_medica.Entity.medicoEntity;
import com.gesalud.receta_medica.Entity.pacienteEntity;
import com.gesalud.receta_medica.Service.medicoService;
import com.gesalud.receta_medica.constants.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medico")
@CrossOrigin(origins = AppConstants.FRONTEND_URL)
public class medicoController {
    private medicoService medService;

    @Autowired
    public medicoController(medicoService medService) {
        this.medService = medService;
    }

    @GetMapping("/all") //GET -> /medico/all
    public ResponseEntity<List<medicoEntity>> obtenerTodosLosMedicos(){
        List<medicoEntity> medico = medService.obtenerMedicoAll();
        return ResponseEntity.ok(medico);
    }

    @GetMapping //GET -> /medico
    public ResponseEntity<List<medicoEntity>> todosLosMedicosActivos(){
        List<medicoEntity> medico = medService.obtenerMedicoActivos();
        return ResponseEntity.ok(medico);//new
    }

    @GetMapping("/id/{id}") //GET -> /medico/id/1
    public ResponseEntity<Optional<medicoEntity>> obtenermedicoPorId(@PathVariable Long id) {
        Optional<medicoEntity> medico = medService.medicoByIdActivo(id);
        return ResponseEntity.ok(medico);
    }

    @PostMapping // POST -> /medico
    public ResponseEntity<?> crearMedico(@RequestBody medicoEntity nuevoMedico) {
        return medService.crearMedicoConValidacion(nuevoMedico);
    }

    @PutMapping("/{id}") // PUT -> /medico/{id}
    public ResponseEntity<medicoEntity> actualizarMedico(@PathVariable Long id, @RequestBody medicoEntity medicoActualizada) {
        Optional<medicoEntity> medicoExistente = medService.obtenerMedicoById(id);
        if (medicoExistente.isPresent()) {
            medicoEntity medico = medicoExistente.get();

            if (medicoActualizada.getEspecialidad() != null && !medicoActualizada.getEspecialidad().isBlank()) {
                medico.setEspecialidad(medicoActualizada.getEspecialidad());
            }
            if (medicoActualizada.getActivo() != null) {
                medico.setActivo(medicoActualizada.getActivo());
            }

            return new ResponseEntity<>(medService.guardarMedico(medico), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}") // DELETE lógico -> /medico/{id}
    public ResponseEntity<Void> eliminarMedicoLogicamente(@PathVariable Long id) {
        Optional<medicoEntity> medicoExistente = medService.medicoByIdActivo(id);
        if (medicoExistente.isPresent()) {
            medicoEntity medico = medicoExistente.get();
            medico.setActivo(false);
            medService.guardarMedico(medico);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
