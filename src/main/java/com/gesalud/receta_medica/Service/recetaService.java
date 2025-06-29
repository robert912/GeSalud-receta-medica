package com.gesalud.receta_medica.Service;

import com.gesalud.receta_medica.Entity.medicoEntity;
import com.gesalud.receta_medica.Entity.pacienteEntity;
import com.gesalud.receta_medica.Entity.recetaEntity;
import com.gesalud.receta_medica.Repository.recetaRepository;
import com.gesalud.receta_medica.dto.recetaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class recetaService {
    private recetaRepository recetaRepository;
    private pacienteService pacienteService;
    private medicoService medicoService;

    @Autowired
    public recetaService(recetaRepository recetaRepo, pacienteService pacienteServ, medicoService medicoServ) {
        this.pacienteService = pacienteServ;
        this.medicoService = medicoServ;
        this.recetaRepository = recetaRepo;
    }

    public recetaEntity guardarReceta(recetaEntity receta) {
        return recetaRepository.save(receta);
    }

    public recetaEntity guardarRecetaDTO(recetaDTO recetaDto) {
        recetaEntity nuevaReceta = new recetaEntity();

        Optional<pacienteEntity> pacienteOpt = pacienteService.buscarPorId(recetaDto.getPaciente());
        Optional<medicoEntity> medicoOpt = medicoService.buscarPorId(recetaDto.getMedico());

        if (pacienteOpt.isEmpty() || medicoOpt.isEmpty()) {
            throw new RuntimeException("Paciente o Médico no encontrado");
        }

        nuevaReceta.setPaciente(pacienteOpt.get());
        nuevaReceta.setMedico(medicoOpt.get());
        nuevaReceta.setFechaCreacion(LocalDateTime.now());
        nuevaReceta.setDisponible(true);
        nuevaReceta.setActivo(true);

        return recetaRepository.save(nuevaReceta);
    }

    // Todas los receta activas e inactivas
    public List<recetaEntity> obtenerRecetaAll() {
        return recetaRepository.findAll();
    }

    // Solo receta activos por fecha de creacion Desc
    public List<recetaEntity> obtenerRecetaActivos() {
        return recetaRepository.findByActivoTrueOrderByFechaCreacionDesc();
    }

    // Solo receta activas por medico y fecha de creacion Desc
    public List<recetaEntity> obtenerRecetaActivosPorMedico(Long id_medico) {
        return recetaRepository.findByActivoTrueAndMedicoIdOrderByFechaCreacionDesc(id_medico);
    }

    // Receta por rut
    public List<recetaEntity> obtenerRecetasActivasPorRutPaciente(String rut) {
        return recetaRepository.findByDisponibleTrueAndActivoTrueAndPacientePersonaRut(rut);
    }

    // Para PUT
    public Optional<recetaEntity> obtenerRecetaById(Long id) {
        return recetaRepository.findById(id);
    }

    // Para bloquear la receta
    public Optional<recetaEntity> buscarRecetaPorId(Long id) {
        return recetaRepository.findByIdAndDisponibleTrue(id);
    }

    // Para DELETE lógico por ID
    public Optional<recetaEntity> recetaByIdActivo(Long id) {
        return recetaRepository.findByIdAndActivoTrue(id);
    }

    public Optional<recetaEntity> buscarPorId(Long id) {
        return recetaRepository.findById(id);
    }
}
