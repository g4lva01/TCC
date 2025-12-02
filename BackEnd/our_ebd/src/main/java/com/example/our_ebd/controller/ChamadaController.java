package com.example.our_ebd.controller;

import com.example.our_ebd.dto.ChamadaRegistroDTO;
import com.example.our_ebd.dto.ChamadaRespostaDTO;
import com.example.our_ebd.dto.FrequenciaAlunoDTO;
import com.example.our_ebd.model.Chamada;
import com.example.our_ebd.model.Pessoa;
import com.example.our_ebd.model.Presenca;
import com.example.our_ebd.model.Turma;
import com.example.our_ebd.repository.ChamadaRepository;
import com.example.our_ebd.repository.PessoaRepository;
import com.example.our_ebd.repository.PresencaRepository;
import com.example.our_ebd.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/chamada")
public class ChamadaController {
    @Autowired
    private ChamadaRepository chamadaRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private PresencaRepository presencaRepository;

    @PostMapping
    public ResponseEntity<?> registrarChamada(@RequestBody ChamadaRegistroDTO dto) {
        Turma turma = turmaRepository.findById(dto.getTurmaId())
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        Chamada chamada = new Chamada();
        chamada.setTurma(turma);
        chamada.setDataChamada(dto.getDataChamada());
        chamada.setStatusChamada(dto.getStatusChamada());
        chamada.setValorOferta(dto.getValorOferta());
        chamada.setQtdVisitantes(dto.getQtdVisitantes());

        List<Presenca> presencas = dto.getPresencas().stream().map(p -> {
            if (p.getAlunoId() == null) {
                throw new IllegalArgumentException("alunoId não pode ser nulo");
            }
            Pessoa aluno = pessoaRepository.findById(p.getAlunoId())
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
            Presenca presenca = new Presenca();
            presenca.setAluno(aluno);
            presenca.setPresente(p.getPresente());
            presenca.setLevouBiblia(p.getLevouBiblia());
            presenca.setLevouRevista(p.getLevouRevista());
            presenca.setChamada(chamada);
            return presenca;
        }).collect(Collectors.toCollection(ArrayList::new));

        chamada.setPresencas(presencas);

        Chamada chamadaSalva = chamadaRepository.save(chamada);
        ChamadaRespostaDTO resposta = new ChamadaRespostaDTO(
                chamadaSalva.getId(),
                chamadaSalva.getDataChamada(),
                chamadaSalva.getStatusChamada(),
                chamadaSalva.getValorOferta(),
                chamadaSalva.getQtdVisitantes()
        );

        return ResponseEntity.ok(resposta);
    }

    @GetMapping
    public List<Chamada> listarChamadas() {
        return chamadaRepository.findAll();
    }

    @GetMapping("/frequencia/trimestre/{turmaId}")
    public ResponseEntity<List<FrequenciaAlunoDTO>> getFrequenciaTrimestral(@PathVariable Long turmaId) {
        Turma turma = turmaRepository.findById(turmaId)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        // Define trimestre atual
        LocalDate hoje = LocalDate.now();
        int trimestre = (hoje.getMonthValue() - 1) / 3 + 1;
        LocalDate inicio = LocalDate.of(hoje.getYear(), (trimestre - 1) * 3 + 1, 1);
        LocalDate fim = inicio.plusMonths(3).minusDays(1);

        // Conta domingos no trimestre
        int domingosCount = 0;
        for (LocalDate d = inicio; !d.isAfter(fim); d = d.plusDays(1)) {
            if (d.getDayOfWeek() == DayOfWeek.SUNDAY) domingosCount++;
        }
        final int domingos = domingosCount; // agora é final

        List<Pessoa> alunos = pessoaRepository.findByTurma(turma);

        List<FrequenciaAlunoDTO> resultado = alunos.stream()
                .map(aluno -> {
                    long presencas = presencaRepository
                            .countByAlunoAndPresenteTrueAndChamada_DataChamadaBetween(aluno, inicio, fim);
                    double percentual = domingos > 0 ? (presencas * 100.0) / domingos : 0.0;
                    return new FrequenciaAlunoDTO(
                            aluno.getNome(),
                            aluno.getTurma().getNome(),
                            presencas,
                            domingos,
                            percentual
                    );
                })
                .toList();

        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/frequencia/trimestre/todas")
    public ResponseEntity<List<FrequenciaAlunoDTO>> getFrequenciaTrimestralTodasTurmas() {
        LocalDate hoje = LocalDate.now();
        int trimestre = (hoje.getMonthValue() - 1) / 3 + 1;
        LocalDate inicio = LocalDate.of(hoje.getYear(), (trimestre - 1) * 3 + 1, 1);
        LocalDate fim = inicio.plusMonths(3).minusDays(1);

        int domingos = (int) Stream.iterate(inicio, d -> d.plusDays(1))
                .limit(ChronoUnit.DAYS.between(inicio, fim) + 1)
                .filter(d -> d.getDayOfWeek() == DayOfWeek.SUNDAY)
                .count();

        List<Pessoa> alunos = pessoaRepository.findAll();

        List<FrequenciaAlunoDTO> resultado = alunos.stream()
                .filter(aluno -> aluno.getTurma() != null)
                .map(aluno -> {
                    long presencas = presencaRepository
                            .countByAlunoAndPresenteTrueAndChamada_DataChamadaBetween(aluno, inicio, fim);
                    double percentual = domingos > 0 ? (presencas * 100.0) / domingos : 0.0;
                    return new FrequenciaAlunoDTO(
                            aluno.getNome(),
                            aluno.getTurma().getNome(),
                            presencas,
                            domingos,
                            percentual
                    );
                })
                .toList();

        return ResponseEntity.ok(resultado);
    }
}
