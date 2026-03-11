package com.example.our_ebd.service;

import com.example.our_ebd.dto.HistoricoAlunoDTO;
import com.example.our_ebd.dto.PresencaResumoDTO;
import com.example.our_ebd.model.Chamada;
import com.example.our_ebd.model.Pessoa;
import com.example.our_ebd.model.Presenca;
import com.example.our_ebd.model.Turma;
import com.example.our_ebd.repository.ChamadaRepository;
import com.example.our_ebd.repository.PessoaRepository;
import com.example.our_ebd.repository.PresencaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
