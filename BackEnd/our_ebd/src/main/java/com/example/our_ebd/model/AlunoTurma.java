package com.example.our_ebd.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@IdClass(com.example.our_ebd.model.AlunoTurma.class)
public class AlunoTurma {
    @Id
    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Pessoa aluno;

    @Id
    @ManyToOne
    @JoinColumn(name = "turma_id")
    private Turma turma;

    private LocalDate dataMatricula;

    public Pessoa getAluno() {
        return aluno;
    }

    public void setAluno(Pessoa aluno) {
        this.aluno = aluno;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula = dataMatricula;
    }
}
