package com.example.our_ebd.service;

import com.example.our_ebd.dto.PresencaResumoDTO;
import com.example.our_ebd.model.Chamada;
import com.example.our_ebd.model.Presenca;
import com.example.our_ebd.repository.ChamadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PresencaService {

    @Autowired
    private ChamadaRepository chamadaRepo;

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
}
