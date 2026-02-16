package com.example.our_ebd.controller;

import com.example.our_ebd.dto.HistoricoDTO;
import com.example.our_ebd.dto.PresencaResumoDTO;
import com.example.our_ebd.model.Chamada;
import com.example.our_ebd.model.Presenca;
import com.example.our_ebd.model.Turma;
import com.example.our_ebd.repository.ChamadaRepository;
import com.example.our_ebd.repository.PresencaRepository;
import com.example.our_ebd.repository.TurmaRepository;
import com.example.our_ebd.service.PresencaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/presencas")
public class PresencaController {
    @Autowired
    private PresencaRepository presencaRepository;

    @Autowired
    private ChamadaRepository chamadaRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private PresencaService presencaService;

    @PostMapping
    public Presenca registrar(@RequestBody Presenca presenca) {
        return presencaRepository.save(presenca);
    }

    @GetMapping("/chamada/{chamadaId}")
    public List<Presenca> listarPorChamada(@PathVariable Long chamadaId) {
        return presencaRepository.findByChamadaId(chamadaId);
    }

    @GetMapping("/historico/turma/{nomeTurma}")
    public ResponseEntity<?> getHistorico(
            @PathVariable String nomeTurma,
            @RequestParam int trimestre,
            @RequestParam int ano) {

        Turma turma = turmaRepository.findByNomeIgnoreCase(nomeTurma)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        LocalDate inicio;
        LocalDate fim;

        switch (trimestre) {
            case 1 -> { inicio = LocalDate.of(ano, 1, 1); fim = LocalDate.of(ano, 3, 31); }
            case 2 -> { inicio = LocalDate.of(ano, 4, 1); fim = LocalDate.of(ano, 6, 30); }
            case 3 -> { inicio = LocalDate.of(ano, 7, 1); fim = LocalDate.of(ano, 9, 30); }
            case 4 -> { inicio = LocalDate.of(ano, 10, 1); fim = LocalDate.of(ano, 12, 31); }
            default -> throw new IllegalArgumentException("Trimestre inválido");
        }

        List<HistoricoDTO> resultado = new ArrayList<>();

        LocalDate data = inicio.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        while (!data.isAfter(fim)) {
            Optional<Chamada> chamadaOpt = chamadaRepository.findByTurmaAndDataChamada(turma, data);

            if (chamadaOpt.isPresent()) {
                Chamada chamada = chamadaOpt.get();

                long presentes = chamada.getPresencas().stream()
                        .filter(Presenca::getPresente)
                        .count();

                long biblias = chamada.getPresencas().stream()
                        .filter(Presenca::getLevouBiblia)
                        .count();

                long revistas = chamada.getPresencas().stream()
                        .filter(Presenca::getLevouRevista)
                        .count();

                int visitantes = chamada.getQtdVisitantes() != null ? chamada.getQtdVisitantes() : 0;

                resultado.add(new HistoricoDTO(
                        chamada.getDataChamada(),
                        presentes,
                        biblias,
                        revistas,
                        visitantes
                ));
            } else {
                resultado.add(new HistoricoDTO(data, 0, 0, 0, 0));
            }
            data = data.plusWeeks(1);
        }

        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/historico/data/{data}")
    public List<PresencaResumoDTO> getHistoricoPorData(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return presencaService.buscarPorData(data);
    }
}
