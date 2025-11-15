package com.example.our_ebd.controller;

import com.example.our_ebd.model.Turma;
import com.example.our_ebd.repository.PresencaRepository;
import com.example.our_ebd.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/alunos")
@CrossOrigin(origins = "*")
public class AlunoRelatorioController {
    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private PresencaRepository presencaRepository;

    @GetMapping("/{id}/relatorio")
    public ResponseEntity<?> relatorioAluno(@PathVariable Long id) {
        List<Long> turmaIds = presencaRepository.findDistinctTurmaIdsByAlunoId(id);

        List<Map<String, Object>> relatorio = turmaIds.stream()
                .map(turmaId -> {
                    Turma turma = turmaRepository.findById(turmaId).orElse(null);
                    if (turma == null) return null;

                    long frequencia = presencaRepository.countByAluno_IdAndChamada_Turma_IdAndPresenteTrue(id, turmaId);
                    long biblia = presencaRepository.countByAluno_IdAndChamada_Turma_IdAndLevouBibliaTrue(id, turmaId);

                    Map<String, Object> mapa = new HashMap<>();
                    mapa.put("turma", turma.getNome());
                    mapa.put("frequencia", frequencia);
                    mapa.put("biblia", biblia);

                    return mapa;
                })
                .filter(Objects::nonNull)
                .toList();

        return ResponseEntity.ok(relatorio);
    }
}
