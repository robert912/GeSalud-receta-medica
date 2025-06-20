package com.gesalud.receta_medica.Service;

import com.gesalud.receta_medica.Entity.medicamentoEntity;
import com.gesalud.receta_medica.Repository.medicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class medicamentoService {
    private medicamentoRepository medicamentoRepository;

    @Autowired
    public medicamentoService(medicamentoRepository medicamentoRepo) {
        this.medicamentoRepository = medicamentoRepo;
    }

    public medicamentoEntity guardarMedicamento(medicamentoEntity medicamento) {
        return medicamentoRepository.save(medicamento);
    }

    // Todas los medicamento activas e inactivas
    public List<medicamentoEntity> obtenermedicamentoAll() {
        return medicamentoRepository.findAll();
    }

    // Solo medicamento activos
    public List<medicamentoEntity> obtenerMedicamentoActivos() {
        return medicamentoRepository.findByActivoTrue();
    }

    // nombre por nombre
    public Optional<medicamentoEntity> obtenerMedicamentoByNombre(String nombre) {
        return medicamentoRepository.findByNombre(nombre)
                .filter(medicamento -> medicamento.getActivo()); // filtrar solo activos
    }

    // Para PUT
    public Optional<medicamentoEntity> obtenerMedicamentoById(Long id) {
        return medicamentoRepository.findById(id);
    }

    // Para DELETE lógico por ID
    public Optional<medicamentoEntity> medicamentoByIdActivo(Long id) {
        return medicamentoRepository.findByIdAndActivoTrue(id);
    }
}
