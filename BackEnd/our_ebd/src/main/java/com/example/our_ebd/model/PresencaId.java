package com.example.our_ebd.model;

import java.util.Objects;

public class PresencaId {
    private Long chamada;
    private Long aluno;

    public PresencaId() {}

    public PresencaId(Long chamada, Long aluno) {
        this.chamada = chamada;
        this.aluno = aluno;
    }

    public Long getChamada() {
        return chamada;
    }

    public void setChamada(Long chamada) {
        this.chamada = chamada;
    }

    public Long getAluno() {
        return aluno;
    }

    public void setAluno(Long aluno) {
        this.aluno = aluno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PresencaId)) return false;
        PresencaId that = (PresencaId) o;
        return Objects.equals(chamada, that.chamada) &&
                Objects.equals(aluno, that.aluno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chamada, aluno);
    }
}
