package com.example.our_ebd.controller;

import com.example.our_ebd.model.Presenca;
import com.example.our_ebd.repository.PresencaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/presencas")
@CrossOrigin(origins = "*")
public class PresencaController {
    @Autowired
    private PresencaRepository presencaRepository;

    @PostMapping
    public Presenca registrar(@RequestBody Presenca presenca) {
        return presencaRepository.save(presenca);
    }

    @GetMapping("/chamada/{chamadaId}")
    public List<Presenca> listarPorChamada(@PathVariable Long chamadaId) {
        return presencaRepository.findByChamadaId(chamadaId);
    }
}
