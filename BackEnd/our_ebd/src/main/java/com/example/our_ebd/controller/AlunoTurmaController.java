package com.example.our_ebd.controller;

import com.example.our_ebd.model.AlunoTurma;
import com.example.our_ebd.model.Pessoa;
import com.example.our_ebd.model.Turma;
import com.example.our_ebd.repository.AlunoTurmaRepository;
import com.example.our_ebd.repository.PessoaRepository;
import com.example.our_ebd.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;

@RestController
@RequestMapping("/api/matriculas")
@CrossOrigin(origins = "*")
public class AlunoTurmaController {
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private AlunoTurmaRepository alunoTurmaRepository;

    @PostMapping
    public String matricularAluno(@RequestParam Long alunoId, @RequestParam Long turmaId) {
        Pessoa aluno = pessoaRepository.findById(alunoId)
                .orElseThrow(()-> new RuntimeException("Aluno não encontrado"));

        Turma turma = turmaRepository.findById(turmaId)
                .orElseThrow(()-> new RuntimeException("Turma não encontrada"));

        if (aluno.getDataDeNascimento() != null && turma.getLimiteDeIdade() != null) {
            int idade = Period.between(aluno.getDataDeNascimento(), LocalDate.now()).getYears();
            if (idade > turma.getLimiteDeIdade()) {
                return "Aluno tem "+idade+" anos e excede o limitede idade da turma ("+turma.getLimiteDeIdade()+")";
            }
        }

        AlunoTurma matricula = new AlunoTurma();
        matricula.setAluno(aluno);
        matricula.setTurma(turma);
        matricula.setDataMatricula(LocalDate.now());

        alunoTurmaRepository.save(matricula);
        return "Aluno matriculado com sucesso!";
    }
}
