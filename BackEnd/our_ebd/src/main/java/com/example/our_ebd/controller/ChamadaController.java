package com.example.our_ebd.controller;

import com.example.our_ebd.dto.ChamadaRegistroDTO;
import com.example.our_ebd.dto.ChamadaRespostaDTO;
import com.example.our_ebd.model.Chamada;
import com.example.our_ebd.model.Pessoa;
import com.example.our_ebd.model.Presenca;
import com.example.our_ebd.model.Turma;
import com.example.our_ebd.repository.ChamadaRepository;
import com.example.our_ebd.repository.PessoaRepository;
import com.example.our_ebd.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chamada")
public class ChamadaController {
    @Autowired
    private ChamadaRepository chamadaRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private TurmaRepository turmaRepository;

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

    @GetMapping("/turma/{turmaId}")
    public List<Chamada> listarPorTurma(@PathVariable Long turmaId) {
        return chamadaRepository.findByTurmaId(turmaId);
    }
}
