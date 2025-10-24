package com.example.our_ebd.controller;

import com.example.our_ebd.model.Atividade;
import com.example.our_ebd.model.Pessoa;
import com.example.our_ebd.repository.AtividadeRepository;
import com.example.our_ebd.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/atividade")
@CrossOrigin(origins = "*")
public class AtividadeController {
    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @GetMapping
    public List<Atividade> listarTodas() {
        return atividadeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Atividade> buscarPorId(@PathVariable Long id) {
        return atividadeRepository.findById(id);
    }

    @GetMapping("/turma/{turmaId}")
    public List<Atividade> listarPorTurma(@PathVariable Long turmaId) {
        return atividadeRepository.findByTurmaId(turmaId);
    }

    @GetMapping("/professor/{professorId}")
    public List<Atividade> listarPorProfessor(@PathVariable Long professorId) {
        return atividadeRepository.findByProfessorId(professorId);
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Atividade atividade) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean isAutorizado = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_PROFESSOR") || a.getAuthority().equals("ROLE_GESTOR"));

        if (!isAutorizado) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Apenas professores ou gestores podem criar atividades.");
        }

        Integer matricula = Integer.parseInt(auth.getName());
        Pessoa autor = pessoaRepository.findByMatricula(matricula)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        atividade.setProfessor(autor); // pode ser professor ou gestor
        atividade.setDataPublicacao(LocalDateTime.now());

        return ResponseEntity.ok(atividadeRepository.save(atividade));
    }

    @PostMapping("/{id}")
    public Atividade atualizar(@PathVariable Long id, @RequestBody Atividade nova) {
       return atividadeRepository.findById(id).map(atividade -> {
           atividade.setTitulo(nova.getTitulo());
           atividade.setDescricao(nova.getDescricao());
           atividade.setDataPublicacao(nova.getDataPublicacao());
           atividade.setTurma(nova.getTurma());
           atividade.setProfessor(nova.getProfessor());
           return atividadeRepository.save(atividade);
       }).orElseGet(()-> {
           nova.setId(id);
           return atividadeRepository.save(nova);
       });
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        atividadeRepository.deleteById(id);
    }
}
