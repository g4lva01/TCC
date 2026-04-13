package com.example.our_ebd.controller;

import com.example.our_ebd.dto.*;
import com.example.our_ebd.model.Chamada;
import com.example.our_ebd.model.Pessoa;
import com.example.our_ebd.model.Presenca;
import com.example.our_ebd.model.Turma;
import com.example.our_ebd.repository.ChamadaRepository;
import com.example.our_ebd.repository.PessoaRepository;
import com.example.our_ebd.repository.PresencaRepository;
import com.example.our_ebd.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
            presenca.setLevouRevista(p.getPresente());
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

        LocalDate hoje = LocalDate.now();
        int trimestre = (hoje.getMonthValue() - 1) / 3 + 1;
        LocalDate inicio = LocalDate.of(hoje.getYear(), (trimestre - 1) * 3 + 1, 1);
        LocalDate fim = inicio.plusMonths(3).minusDays(1);

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

    @GetMapping("/frequencia/trimestre/aluno/{alunoId}")
    public ResponseEntity<FrequenciaAlunoDTO> getFrequenciaTrimestralAluno(@PathVariable Long alunoId) {
        Pessoa aluno = pessoaRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        Turma turma = aluno.getTurma();
        if (turma == null) {
            throw new RuntimeException("Aluno não está matriculado em nenhuma turma");
        }

        LocalDate hoje = LocalDate.now();
        int trimestre = (hoje.getMonthValue() - 1) / 3 + 1;
        LocalDate inicio = LocalDate.of(hoje.getYear(), (trimestre - 1) * 3 + 1, 1);
        LocalDate fim = inicio.plusMonths(3).minusDays(1);

        int domingos = (int) Stream.iterate(inicio, d -> d.plusDays(1))
                .limit(ChronoUnit.DAYS.between(inicio, fim) + 1)
                .filter(d -> d.getDayOfWeek() == DayOfWeek.SUNDAY)
                .count();

        long presencas = presencaRepository
                .countByAlunoAndPresenteTrueAndChamada_DataChamadaBetween(aluno, inicio, fim);

        double percentual = domingos > 0 ? (presencas * 100.0) / domingos : 0.0;

        FrequenciaAlunoDTO resultado = new FrequenciaAlunoDTO(
                aluno.getNome(),
                turma.getNome(),
                presencas,
                domingos,
                percentual
        );

        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{turmaNome}/{data}")
    public ResponseEntity<?> buscarChamadaPorTurmaENomeEData(
            @PathVariable String turmaNome,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        Turma turma = turmaRepository.findByNomeIgnoreCase(turmaNome)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        Optional<Chamada> chamadaOpt = chamadaRepository.findByTurmaAndDataChamada(turma, data);

        if (chamadaOpt.isPresent()) {
            Chamada c = chamadaOpt.get();

            int totalMatriculados = c.getPresencas().size();
            int totalPresentes = (int) c.getPresencas().stream().filter(Presenca::getPresente).count();
            int totalAusentes = totalMatriculados - totalPresentes;
            int totalBiblias = (int) c.getPresencas().stream().filter(Presenca::getLevouBiblia).count();
            int totalRevistas = (int) c.getPresencas().stream().filter(Presenca::getLevouRevista).count();

            List<PresencaEdicaoDTO> presencasDTO = c.getPresencas().stream()
                    .map(p -> new PresencaEdicaoDTO(
                            p.getAluno().getId(),
                            p.getAluno().getNome(),
                            p.getPresente(),
                            p.getLevouBiblia(),
                            p.getLevouRevista()
                    ))
                    .toList();

            ChamadaComPresencasDTO dto = new ChamadaComPresencasDTO(
                    c.getId(),
                    c.getDataChamada(),
                    c.getStatusChamada(),
                    c.getValorOferta(),
                    c.getQtdVisitantes(),
                    presencasDTO
            );

            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Nenhuma chamada encontrada para essa turma e data.");
        }
    }

    @GetMapping("/frequencia/trimestre/top-presencas")
    public ResponseEntity<List<AlunoFrequenciaDTO>> getTopPresencas(@RequestParam int ano, @RequestParam int trimestre){
        LocalDate inicio;
        LocalDate fim;

        switch (trimestre) {
            case 1: inicio = LocalDate.of(ano, 1, 1); fim = LocalDate.of(ano, 3, 31); break;
            case 2: inicio = LocalDate.of(ano, 4, 1); fim = LocalDate.of(ano, 6, 30); break;
            case 3: inicio = LocalDate.of(ano, 7, 1); fim = LocalDate.of(ano, 9, 30); break;
            case 4: inicio = LocalDate.of(ano, 10, 1); fim = LocalDate.of(ano, 12, 31); break;
            default: throw new IllegalArgumentException("Trimestre inválido");
        }

        List<AlunoFrequenciaDTO> resultado = presencaRepository.findTopPresencas(inicio, fim);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/frequencia/trimestre/top-faltas")
    public ResponseEntity<List<AlunoFrequenciaDTO>> getTopFaltas(@RequestParam int ano, @RequestParam int trimestre) {
        LocalDate inicio;
        LocalDate fim;

        switch (trimestre) {
            case 1: inicio = LocalDate.of(ano, 1, 1); fim = LocalDate.of(ano, 3, 31); break;
            case 2: inicio = LocalDate.of(ano, 4, 1); fim = LocalDate.of(ano, 6, 30); break;
            case 3: inicio = LocalDate.of(ano, 7, 1); fim = LocalDate.of(ano, 9, 30); break;
            case 4: inicio = LocalDate.of(ano, 10, 1); fim = LocalDate.of(ano, 12, 31); break;
            default: throw new IllegalArgumentException("Trimestre inválido");
        }

        List<AlunoFrequenciaDTO> resultado = presencaRepository.findTopFaltas(inicio, fim);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/frequencia/trimestre/turmas")
    public ResponseEntity<List<TurmaFrequenciaDTO>> getTurmasComMaiorFrequencia(@RequestParam int ano, @RequestParam int trimestre) {
        LocalDate inicio;
        LocalDate fim;

        switch (trimestre) {
            case 1: inicio = LocalDate.of(ano, 1, 1); fim = LocalDate.of(ano, 3, 31); break;
            case 2: inicio = LocalDate.of(ano, 4, 1); fim = LocalDate.of(ano, 6, 30); break;
            case 3: inicio = LocalDate.of(ano, 7, 1); fim = LocalDate.of(ano, 9, 30); break;
            case 4: inicio = LocalDate.of(ano, 10, 1); fim = LocalDate.of(ano, 12, 31); break;
            default: throw new IllegalArgumentException("Trimestre inválido");
        }

        List<TurmaFrequenciaDTO> resultado = presencaRepository.findTurmasComMaiorFrequencia(inicio, fim);
        return  ResponseEntity.ok(resultado);
    }

    @GetMapping("/frequencia/trimestre/faltas-por-dia")
    public ResponseEntity<List<FaltasDiaDTO>> getDiasComMaisFaltas(@RequestParam int ano, @RequestParam int trimestre) {
        LocalDate inicio;
        LocalDate fim;

        switch (trimestre) {
            case 1: inicio = LocalDate.of(ano, 1, 1); fim = LocalDate.of(ano, 3, 31); break;
            case 2: inicio = LocalDate.of(ano, 4, 1); fim = LocalDate.of(ano, 6, 30); break;
            case 3: inicio = LocalDate.of(ano, 7, 1); fim = LocalDate.of(ano, 9, 30); break;
            case 4: inicio = LocalDate.of(ano, 10, 1); fim = LocalDate.of(ano, 12, 31); break;
            default: throw new IllegalArgumentException("Trimestre inválido");
        }

        List<FaltasDiaDTO> resultado = presencaRepository.findDiasComMaisFaltas(inicio, fim);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/frequencia/trimestre/dias-sem-chamada")
    public ResponseEntity<List<AvisoSemChamadaDTO>> getDiasSemChamada(
            @RequestParam int ano, @RequestParam int trimestre) {

        LocalDate inicio;
        LocalDate fim;

        switch (trimestre) {
            case 1: inicio = LocalDate.of(ano, 1, 1); fim = LocalDate.of(ano, 3, 31); break;
            case 2: inicio = LocalDate.of(ano, 4, 1); fim = LocalDate.of(ano, 6, 30); break;
            case 3: inicio = LocalDate.of(ano, 7, 1); fim = LocalDate.of(ano, 9, 30); break;
            case 4: inicio = LocalDate.of(ano, 10, 1); fim = LocalDate.of(ano, 12, 31); break;
            default: throw new IllegalArgumentException("Trimestre inválido");
        }

        LocalDate hoje = LocalDate.now();
        if (fim.isAfter(hoje)) {
            fim = hoje;
        }

        List<LocalDate> chamadasGlobais = chamadaRepository.findDatasChamadas(inicio, fim);
        List<Turma> turmas = turmaRepository.findAll();
        List<AvisoSemChamadaDTO> avisos = new ArrayList<>();

        for (Turma turma : turmas) {
            List<LocalDate> chamadasTurma = chamadaRepository.findDatasChamadasPorTurma(turma.getNome(), inicio, fim);

            List<LocalDate> diasSemChamada = new ArrayList<>();
            for (LocalDate data : chamadasGlobais) {
                if (!chamadasTurma.contains(data)) {
                    diasSemChamada.add(data);
                }
            }

            if (!diasSemChamada.isEmpty()) {
                avisos.add(new AvisoSemChamadaDTO(turma.getNome(), diasSemChamada));
            }
        }

        return ResponseEntity.ok(avisos);
    }

    @Transactional
    @PutMapping("/{turmaNome}/{data}")
    public ResponseEntity<?> atualizarChamada(
            @PathVariable String turmaNome,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestBody ChamadaRegistroDTO dto) {

        Turma turma = turmaRepository.findByNomeIgnoreCase(turmaNome)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        Chamada chamada = chamadaRepository.findByTurmaAndDataChamada(turma, data)
                .orElseThrow(() -> new RuntimeException("Chamada não encontrada"));

        chamada.setStatusChamada(dto.getStatusChamada());
        chamada.setValorOferta(dto.getValorOferta());
        chamada.setQtdVisitantes(dto.getQtdVisitantes());

        presencaRepository.deleteByChamada(chamada);
        presencaRepository.flush();

        List<Presenca> novasPresencas = new ArrayList<>();

        for (PresencaDTO p : dto.getPresencas()) {
            Pessoa aluno = pessoaRepository.findById(p.getAlunoId())
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

            Presenca presenca = new Presenca();
            presenca.setAluno(aluno);
            presenca.setPresente(p.getPresente());
            presenca.setLevouBiblia(p.getLevouBiblia());
            presenca.setLevouRevista(p.getLevouRevista());
            presenca.setChamada(chamada);

            novasPresencas.add(presenca);
        }

        chamada.setPresencas(novasPresencas);

        Chamada chamadaAtualizada = chamadaRepository.save(chamada);

        return ResponseEntity.ok(new ChamadaRespostaDTO(
                chamadaAtualizada.getId(),
                chamadaAtualizada.getDataChamada(),
                chamadaAtualizada.getStatusChamada(),
                chamadaAtualizada.getValorOferta(),
                chamadaAtualizada.getQtdVisitantes()
        ));
    }
}
