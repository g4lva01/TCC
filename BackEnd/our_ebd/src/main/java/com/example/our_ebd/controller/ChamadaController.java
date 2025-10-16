package com.example.our_ebd.controller;

import com.example.our_ebd.model.Chamada;
import com.example.our_ebd.repository.ChamadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chamada")
@CrossOrigin(origins = "*")
public class ChamadaController {
    @Autowired
    private ChamadaRepository chamadaRepository;

    public Chamada registrarChamada(@RequestBody Chamada chamada) {
        return chamadaRepository.save(chamada);
    }

    @GetMapping
    public List<Chamada> listarChamadas() {
        return chamadaRepository.findAll();
    }

    @GetMapping("/turma/{turmaId")
    public List<Chamada> listarPorTurma(@PathVariable Long turmaId) {
        return chamadaRepository.findByTurmaId(turmaId);
    }
}
