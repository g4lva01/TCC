package com.example.our_ebd.service;

import com.example.our_ebd.dto.PresencaResumoDTO;
import com.example.our_ebd.model.Chamada;
import com.example.our_ebd.model.Presenca;
import com.example.our_ebd.model.Turma;
import com.example.our_ebd.repository.ChamadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RelatorioService {

    @Autowired
    private ChamadaRepository chamadaRepo;

    public PresencaResumoDTO gerarRelatorio(Turma turma, LocalDate data) {
        Chamada chamada = chamadaRepo.findByTurmaAndDataChamada(turma, data)
                .orElseThrow(() -> new RuntimeException("Chamada não encontrada"));

        int matriculados = chamada.getPresencas().size();
        int presentes = (int) chamada.getPresencas().stream().filter(Presenca::getPresente).count();
        int ausentes = matriculados - presentes;
        int biblia = (int) chamada.getPresencas().stream().filter(Presenca::getLevouBiblia).count();
        int revistas = (int) chamada.getPresencas().stream().filter(Presenca::getLevouRevista).count();

        return new PresencaResumoDTO(
                chamada.getTurma().getNome(),
                matriculados,
                presentes,
                ausentes,
                biblia,
                revistas,
                chamada.getValorOferta() != null ? chamada.getValorOferta().doubleValue() : 0.0
        );
    }
}

