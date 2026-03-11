package com.example.our_ebd.service;

import com.example.our_ebd.dto.HistoricoAlunoDTO;
import com.example.our_ebd.dto.PresencaResumoDTO;
import com.example.our_ebd.dto.RelatorioAlunoDTO;
import com.example.our_ebd.model.Chamada;
import com.example.our_ebd.model.Pessoa;
import com.example.our_ebd.model.Presenca;
import com.example.our_ebd.model.Turma;
import com.example.our_ebd.repository.ChamadaRepository;
import com.example.our_ebd.repository.PessoaRepository;
import com.example.our_ebd.repository.PresencaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PresencaService {

    @Autowired
    private ChamadaRepository chamadaRepo;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PresencaRepository presencaRepository;

    public List<PresencaResumoDTO> buscarPorData(LocalDate data) {
        List<Chamada> chamadas = chamadaRepo.findByDataChamada(data);

        return chamadas.stream().map(chamada -> {
            List<Presenca> presencas = chamada.getPresencas();
            int matriculados = presencas.size();
            int presentes = (int) presencas.stream().filter(Presenca::getPresente).count();
            int ausentes = matriculados - presentes;
            int biblia = (int) presencas.stream().filter(Presenca::getLevouBiblia).count();
            int revistas = (int) presencas.stream().filter(Presenca::getLevouRevista).count();
            int visitantes = chamada.getQtdVisitantes() != null ? chamada.getQtdVisitantes() : 0;
            double oferta = chamada.getValorOferta() != null ? chamada.getValorOferta().doubleValue() : 0.0;

            return new PresencaResumoDTO(chamada.getTurma().getNome(), matriculados, presentes, ausentes, biblia, revistas, visitantes, oferta);
        }).toList();
    }

    public List<HistoricoAlunoDTO> buscarHistoricoPorAluno(String nomeAluno) {
        Pessoa aluno = pessoaRepository.findByNomeIgnoreCase(nomeAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        List<Presenca> presencas = presencaRepository.findByAluno(aluno);

        Map<Turma, List<Presenca>> agrupadoPorTurma = presencas.stream()
                .collect(Collectors.groupingBy(p -> p.getChamada().getTurma()));

        List<HistoricoAlunoDTO> resultado = new ArrayList<>();

        for (Map.Entry<Turma, List<Presenca>> entry : agrupadoPorTurma.entrySet()) {
            Turma turma = entry.getKey();
            List<Presenca> lista = entry.getValue();

            long presentes = lista.stream().filter(Presenca::getPresente).count();
            long biblias = lista.stream().filter(Presenca::getLevouBiblia).count();
            long revistas = lista.stream().filter(Presenca::getLevouRevista).count();
            long ausencias = lista.size() - presentes;

            resultado.add(new HistoricoAlunoDTO(
                    turma.getNome(),
                    presentes,
                    biblias,
                    revistas,
                    ausencias
            ));
        }

        return resultado;
    }

    public List<RelatorioAlunoDTO> buscarRelatorioPorAlunoETrimestre(String alunoNome, int ano, int trimestre) {
        Pessoa aluno = pessoaRepository.findByNomeIgnoreCase(alunoNome)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        LocalDate inicio;
        LocalDate fim;

        switch (trimestre) {
            case 1: inicio = LocalDate.of(ano, 1, 1); fim = LocalDate.of(ano, 3, 31); break;
            case 2: inicio = LocalDate.of(ano, 4, 1); fim = LocalDate.of(ano, 6, 30); break;
            case 3: inicio = LocalDate.of(ano, 7, 1); fim = LocalDate.of(ano, 9, 30); break;
            case 4: inicio = LocalDate.of(ano, 10, 1); fim = LocalDate.of(ano, 12, 31); break;
            default: throw new IllegalArgumentException("Trimestre inválido");
        }

        return presencaRepository.findByAlunoAndChamada_DataChamadaBetween(aluno, inicio, fim)
                .stream()
                .filter(p -> p.getChamada().getDataChamada().getDayOfWeek() == DayOfWeek.SUNDAY)
                .map(p -> new RelatorioAlunoDTO(
                        p.getChamada().getDataChamada(),
                        Boolean.TRUE.equals(p.getPresente()),
                        Boolean.TRUE.equals(p.getLevouBiblia()),
                        Boolean.TRUE.equals(p.getLevouRevista())
                ))
                .collect(Collectors.toList());
    }
}
