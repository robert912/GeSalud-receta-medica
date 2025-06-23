package com.gesalud.receta_medica.Service;

import com.gesalud.receta_medica.Entity.medicamentoEntity;
import com.gesalud.receta_medica.Entity.recetaEntity;
import com.gesalud.receta_medica.Entity.detalleRecetaEntity;
import com.gesalud.receta_medica.Repository.detalleRecetaRepository;
import com.gesalud.receta_medica.dto.detalleRecetaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class detalleRecetaService {
    private detalleRecetaRepository detalleRecetaRepository;
    private recetaService recetaService;
    private medicamentoService medicamentoService;

    @Autowired
    public detalleRecetaService(detalleRecetaRepository detalleRecetaRepo, recetaService recetaServ, medicamentoService medicamentoServ) {
        this.recetaService = recetaServ;
        this.medicamentoService = medicamentoServ;
        this.detalleRecetaRepository = detalleRecetaRepo;
    }

    public detalleRecetaEntity guardarDetalle(detalleRecetaEntity detalle) {
        return detalleRecetaRepository.save(detalle);
    }

    public detalleRecetaEntity guardarDetalleRecetaDTO(detalleRecetaDTO detalleDto) {
        detalleRecetaEntity nuevoDetalle = new detalleRecetaEntity();

        Optional<recetaEntity> recetaOpt = recetaService.buscarPorId(detalleDto.getReceta());
        Optional<medicamentoEntity> medicinaOpt = medicamentoService.buscarPorId(detalleDto.getMedicamento());

        if (recetaOpt.isEmpty() || medicinaOpt.isEmpty()) {
            throw new RuntimeException("Receta o Medicamento no encontrado");
        }

        nuevoDetalle.setReceta(recetaOpt.get());
        nuevoDetalle.setMedicamento(medicinaOpt.get());
        nuevoDetalle.setConcentracion(detalleDto.getConcentracion());
        nuevoDetalle.setDosis(detalleDto.getDosis());
        nuevoDetalle.setFrecuencia(detalleDto.getFrecuencia());
        nuevoDetalle.setDuracion(detalleDto.getDuracion());
        nuevoDetalle.setActivo(true);

        return detalleRecetaRepository.save(nuevoDetalle);
    }

    // Todas los receta activas e inactivas
    public List<detalleRecetaEntity> obtenerDetalleRecetaAll() {
        return detalleRecetaRepository.findAll();
    }

    // Solo Detalles activos
    public List<detalleRecetaEntity> obtenerDetalleRecetaActivos() {
        return detalleRecetaRepository.findByActivoTrue();
    }

    // Receta por receta
    public List<detalleRecetaEntity> obtenerDetalleRecetasPorReceta(Long id) {
        return detalleRecetaRepository.findByrecetaId(id);
    }

    // Para PUT
    public Optional<detalleRecetaEntity> obtenerDetalleRecetaById(Long id) {
        return detalleRecetaRepository.findById(id);
    }

    // Para bloquear el detalle
    public Optional<detalleRecetaEntity> buscarDetalleRecetaPorId(Long id) {
        return detalleRecetaRepository.findByIdAndActivoTrue(id);
    }

    public Optional<detalleRecetaEntity> buscarPorId(Long id) {
        return detalleRecetaRepository.findById(id);
    }
}
