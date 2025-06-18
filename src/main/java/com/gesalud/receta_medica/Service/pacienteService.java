package com.gesalud.receta_medica.Service;

import com.gesalud.receta_medica.Entity.pacienteEntity;
import com.gesalud.receta_medica.Repository.pacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class pacienteService {
    private pacienteRepository pacienteRepository;

    @Autowired
    public pacienteService(pacienteRepository pacienteRepo) {
        this.pacienteRepository = pacienteRepo;
    }

    public pacienteEntity guardarPaciente(pacienteEntity paciente) {
        return pacienteRepository.save(paciente);
    }

    public List<pacienteEntity> obtenerpacienteAll() {
        return pacienteRepository.findAll();
    }

    public Optional<pacienteEntity> obtenerPacienteById(Integer id) {
        return pacienteRepository.findById(id);
    }
}
