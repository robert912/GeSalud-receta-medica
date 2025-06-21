package com.gesalud.receta_medica.Service;

import com.gesalud.receta_medica.Entity.detalleRecetaEntity;
import com.gesalud.receta_medica.Entity.farmaceuticoEntity;
import com.gesalud.receta_medica.Entity.retiroEntity;
import com.gesalud.receta_medica.Repository.retiroRepository;
import com.gesalud.receta_medica.dto.retiroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class retiroService {
    private retiroRepository retiroRepository;
    private detalleRecetaService detalleRecetaService;
    private farmaceuticoService farmaceuticoService;

    @Autowired
    public retiroService(retiroRepository retiroRepo, detalleRecetaService detalleServ, farmaceuticoService farmaServ) {
        this.detalleRecetaService = detalleServ;
        this.farmaceuticoService = farmaServ;
        this.retiroRepository = retiroRepo;
    }

    public retiroEntity guardarRetiro(retiroEntity retiro) {
        return retiroRepository.save(retiro);
    }

    public retiroEntity guardarRetiroDTO(retiroDTO retiroDto) {
        retiroEntity nuevoRetiro = new retiroEntity();
        Optional<detalleRecetaEntity> detalleOpt = detalleRecetaService.buscarPorId(retiroDto.getDetalleReceta());
        Optional<farmaceuticoEntity> farmaceuticoOpt = farmaceuticoService.buscarPorId(retiroDto.getFarmaceutico());

        if (detalleOpt.isEmpty() || farmaceuticoOpt.isEmpty()) {
            throw new RuntimeException("Detalle o Farmaceutico no encontrado");
        }

        nuevoRetiro.setDetalleReceta(detalleOpt.get());
        nuevoRetiro.setFarmaceutico(farmaceuticoOpt.get());
        nuevoRetiro.setFechaRetiro(LocalDate.now());
        nuevoRetiro.setActivo(true);

        return retiroRepository.save(nuevoRetiro);
    }

    // Todas los retiros activas e inactivas
    public List<retiroEntity> obtenerRetiroAll() {
        return retiroRepository.findAll();
    }

    // Solo receta activos
    public List<retiroEntity> obtenerRetiroActivos() {
        return retiroRepository.findByActivoTrue();
    }

    // Para PUT
    public Optional<retiroEntity> obtenerRetiroById(Long id) {
        return retiroRepository.findById(id);
    }

    // Para DELETE lógico por ID
    public Optional<retiroEntity> retiroByIdActivo(Long id) {
        return retiroRepository.findByIdAndActivoTrue(id);
    }
}
