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
    public ResponseEntity<?> getHistorico(@PathVariable String nomeTurma) {
        Turma turma = turmaRepository.findByNomeIgnoreCase(nomeTurma)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        LocalDate inicio = LocalDate.now().minusYears(3);
        LocalDate fim = LocalDate.now();

        List<HistoricoDTO> resultado = new ArrayList<>();

        LocalDate data = inicio;
        while (!data.isAfter(fim)) {
            if (data.getDayOfWeek() == DayOfWeek.SUNDAY) {
                Optional<Chamada> chamadaOpt = chamadaRepository.findByTurmaAndDataChamada(turma, data);

                if (chamadaOpt.isPresent()) {
                    Chamada chamada = chamadaOpt.get();
                    resultado.add(new HistoricoDTO(
                            chamada.getDataChamada(),
                            chamada.getPresencas().stream().filter(Presenca::getPresente).count(),
                            chamada.getPresencas().stream().filter(Presenca::getLevouBiblia).count(),
                            chamada.getPresencas().stream().filter(Presenca::getLevouRevista).count()
                    ));
                } else {
                    resultado.add(new HistoricoDTO(data, 0, 0, 0));
                }
            }
            data = data.plusDays(1);
        }

        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/historico/data/{data}")
    public List<PresencaResumoDTO> getHistoricoPorData(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return presencaService.buscarPorData(data);
    }
}
