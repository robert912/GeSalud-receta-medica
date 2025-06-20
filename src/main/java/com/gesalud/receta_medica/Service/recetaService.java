package com.gesalud.receta_medica.Service;

import com.gesalud.receta_medica.Entity.recetaEntity;
import com.gesalud.receta_medica.Repository.recetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class recetaService {
    private recetaRepository recetaRepository;

    @Autowired
    public recetaService(recetaRepository recetaRepo) {
        this.recetaRepository = recetaRepo;
    }

    public recetaEntity guardarReceta(recetaEntity receta) {
        return recetaRepository.save(receta);
    }

    // Todas los receta activas e inactivas
    public List<recetaEntity> obtenerRecetaAll() {
        return recetaRepository.findAll();
    }

    // Solo receta activos
    public List<recetaEntity> obtenerRecetaActivos() {
        return recetaRepository.findByActivoTrue();
    }

    // Para PUT
    public Optional<recetaEntity> obtenerRecetaById(Long id) {
        return recetaRepository.findById(id);
    }

    // Para DELETE lógico por ID
    public Optional<recetaEntity> recetaByIdActivo(Long id) {
        return recetaRepository.findByIdAndActivoTrue(id);
    }
}
