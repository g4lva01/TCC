package com.example.our_ebd.controller;

import com.example.our_ebd.model.Pessoa;
import com.example.our_ebd.model.Turma;
import com.example.our_ebd.repository.PessoaRepository;
import com.example.our_ebd.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/turmas")
public class TurmaController {
    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

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

    @GetMapping("/turmas-com-alunos")
    public ResponseEntity<?> listarTurmasComAlunos() {
        List<Turma> turmas = turmaRepository.findAll();

        List<Map<String, Object>> resultado = turmas.stream().map(turma -> {
            List<Pessoa> alunos = pessoaRepository.findByTurma(turma);

            List<Map<String, Object>> dadosAlunos = alunos.stream().map(aluno -> {
                Map<String, Object> dados = new HashMap<>();
                dados.put("id", aluno.getId());
                dados.put("nome", aluno.getNome());
                dados.put("matricula", aluno.getMatricula());
                return dados;
            }).toList();

            Map<String, Object> turmaMap = new HashMap<>();
            turmaMap.put("turma", turma.getNome());
            turmaMap.put("limiteDeIdade", turma.getLimiteDeIdade());
            turmaMap.put("alunos", dadosAlunos);
            return turmaMap;
        }).toList();

        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<?> buscarPorNome(@PathVariable String nome) {
        Optional<Turma> turma = turmaRepository.findByNomeIgnoreCase(nome);
        return turma.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
