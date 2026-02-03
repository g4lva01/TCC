package com.example.our_ebd.controller;

import com.example.our_ebd.dto.AtividadeDTO;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/atividade")
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
    public ResponseEntity<List<AtividadeDTO>> listarPorTurma(@PathVariable Long turmaId) {
        List<Atividade> atividades = atividadeRepository.findByTurmaId(turmaId);
        List<AtividadeDTO> dtos = atividades.stream()
                .map(AtividadeDTO::new)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/professor/{professorId}")
    public List<Atividade> listarPorProfessor(@PathVariable Long professorId) {
        return atividadeRepository.findByProfessorId(professorId);
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Atividade atividade) {
        boolean existe = atividadeRepository.existsByTituloAndNumeroLicaoAndTurmaId(
                atividade.getTitulo(), atividade.getNumeroLicao(), atividade.getTurma().getId()
        );

        if (existe) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Atividade já cadastrada para essa lição e turna.");
        }

        atividade.setDataPublicacao(LocalDate.now());
        Atividade salva = atividadeRepository.save(atividade);

        return ResponseEntity.ok(Map.of("message", "Atividade criada com sucesso!",
                "atividade", salva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Atividade nova) {
        return atividadeRepository.findById(id).map(atividade -> {
            atividade.setTitulo(nova.getTitulo());
            atividade.setDescricao(nova.getDescricao());
            atividade.setTurma(nova.getTurma());
            atividade.setProfessor(nova.getProfessor());
            atividade.setNumeroLicao(nova.getNumeroLicao());
            atividade.setDataPublicacao(LocalDate.now());
            Atividade atualizada = atividadeRepository.save(atividade);
            return ResponseEntity.ok(Map.of("message", "Atividade atualizada com sucesso!", "atividade", atualizada));
        }).orElseGet(() -> {
            nova.setId(id);
            nova.setDataPublicacao(LocalDate.now());
            Atividade criada = atividadeRepository.save(nova);
            return ResponseEntity.ok(Map.of("message", "Atividade criada via PUT!", "atividade", criada));
        });
    }


    @DeleteMapping
    public ResponseEntity<?> deletarPorCampos(@RequestBody Atividade atividade) {
        Optional<Atividade> atividadeOptional = atividadeRepository
                .findByTituloAndNumeroLicaoAndTurmaId(
                        atividade.getTitulo(),
                        atividade.getNumeroLicao(),
                        atividade.getTurma().getId()
                );

        if (atividadeOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Atividade não encontrada."));
        }

        atividadeRepository.delete(atividadeOptional.get());
        return ResponseEntity.ok(Map.of("message", "Atividade deletada com sucesso!"));
    }
}
