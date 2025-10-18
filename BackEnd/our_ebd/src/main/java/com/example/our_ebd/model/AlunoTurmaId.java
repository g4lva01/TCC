package com.example.our_ebd.model;

import java.io.Serializable;
import java.util.Objects;

public class AlunoTurmaId implements Serializable {
    private Long aluno;
    private Long turma;

    public AlunoTurmaId() {}

    public AlunoTurmaId(Long aluno, Long turma) {
        this.aluno = aluno;
        this.turma = turma;
    }

    public Long getAluno() {
        return aluno;
    }

    public void setAluno(Long aluno) {
        this.aluno = aluno;
    }

    public Long getTurma() {
        return turma;
    }

    public void setTurma(Long turma) {
        this.turma = turma;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof AlunoTurmaId)) return false;
        AlunoTurmaId that = (AlunoTurmaId) obj;
        return Objects.equals(aluno, that.aluno) &&
                Objects.equals(turma, that.turma);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aluno, turma);
    }
}
