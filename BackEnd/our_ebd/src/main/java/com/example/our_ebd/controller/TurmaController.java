package com.example.our_ebd.controller;

import com.example.our_ebd.model.Turma;
import com.example.our_ebd.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/turmas")
@CrossOrigin(origins = "*")
public class TurmaController {
    @Autowired
    private TurmaRepository turmaRepository;

    @GetMapping("/{id}")
    public Optional<Turma> buscarPorId(@PathVariable Long id) {
        return turmaRepository.findById(id);
    }

    @PostMapping
    public Turma criar(@RequestBody Turma turma) {
        return turmaRepository.save(turma);
    }

    @PutMapping("/{id}")
    public Turma atualizar(@PathVariable Long id, @RequestBody  Turma turmaAtualizada) {
        return turmaRepository.findById(id).map(turma -> {
          turma.setNome(turmaAtualizada.getNome());
          turma.setLimiteDeIdade(turmaAtualizada.getLimiteDeIdade());
          return turmaRepository.save(turma);
        }).orElseGet(()-> {
            turmaAtualizada.setId(id);
            return turmaRepository.save(turmaAtualizada);
        });
    }
}
