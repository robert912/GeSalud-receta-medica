package com.gesalud.receta_medica.Controller;

import com.gesalud.receta_medica.Entity.pacienteEntity;
import com.gesalud.receta_medica.Entity.recetaEntity;
import com.gesalud.receta_medica.Service.pacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")
@CrossOrigin(origins = "http://localhost:5173")
public class pacienteController {
    private pacienteService pacService;

    @Autowired
    public pacienteController(pacienteService pacService) {
        this.pacService = pacService;
    }

    @GetMapping("/all")//GET -> /paciente/all
    public ResponseEntity<List<pacienteEntity>> obtenerTodosLosPacientes(){
        List<pacienteEntity> paciente = pacService.obtenerPacienteAll();
        return ResponseEntity.ok(paciente);//new
    }

    @GetMapping //GET -> /paciente
    public ResponseEntity<List<pacienteEntity>> todasLosPacientesActivas(){
        List<pacienteEntity> paciente = pacService.obtenerPacienteActivas();
        return ResponseEntity.ok(paciente);//new
    }

    @GetMapping("/{rut}")
    public ResponseEntity<?> obtenerPacientePorRut(@PathVariable String rut) {
        try {
            if (!validarRut(rut)) {
                return ResponseEntity.badRequest()
                        .body("El RUT proporcionado no tiene un formato válido");
            }

            pacienteEntity paciente = pacService.obtenerPacienteActivasPorRut(rut);
            if (paciente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró un paciente con el RUT: " + rut);
            }

            if (paciente.getActivo() != null && !paciente.getActivo()) {
                return ResponseEntity.status(HttpStatus.GONE)
                        .body("El paciente con RUT " + rut + " existe pero no está activo");
            }

            return ResponseEntity.ok(paciente);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar la solicitud: " + e.getMessage());
        }
    }

    @PostMapping // POST -> /paciente
    public ResponseEntity<?> crearPaciente(@RequestBody pacienteEntity nuevoPaciente) {
        return pacService.crearPacienteConValidacion(nuevoPaciente);
    }

    @PutMapping("/{id}") // PUT -> /paciente/{id}
    public ResponseEntity<pacienteEntity> actualizarPaciente(@PathVariable Long id, @RequestBody pacienteEntity pacienteActualizada) {
        Optional<pacienteEntity> pacienteExistente = pacService.obtenerPacienteById(id);
        if (pacienteExistente.isPresent()) {
            pacienteEntity paciente = pacienteExistente.get();

            if (pacienteActualizada.getPrevision() != null && !pacienteActualizada.getPrevision().isBlank()) {
                paciente.setPrevision(pacienteActualizada.getPrevision());
            }
            if (pacienteActualizada.getActivo() != null) {
                paciente.setActivo(pacienteActualizada.getActivo());
            }

            return new ResponseEntity<>(pacService.guardarPaciente(paciente), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}") // DELETE lógico -> /paciente/{id}
    public ResponseEntity<Void> eliminarPacienteLogicamente(@PathVariable Long id) {
        Optional<pacienteEntity> pacienteExistente = pacService.pacienteByIdActivo(id);
        if (pacienteExistente.isPresent()) {
            pacienteEntity paciente = pacienteExistente.get();
            paciente.setActivo(false);
            pacService.guardarPaciente(paciente);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Método auxiliar para validar formato de RUT chileno
    private boolean validarRut(String rut) {
        if (rut == null || rut.isEmpty()) return false;

        // Eliminar puntos y guión si existen
        rut = rut.replace(".", "").replace("-", "");

        // Validar formato básico (al menos 7 dígitos + 1 dígito verificador)
        if (!rut.matches("^[0-9]{7,8}[0-9kK]{1}$")) {
            return false;
        }

        // Aquí podrías agregar validación del dígito verificador si lo necesitas
        return true;
    }
}
