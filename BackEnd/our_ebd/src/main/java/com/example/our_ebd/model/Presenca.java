package com.example.our_ebd.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;

@Entity
@IdClass(Presenca.class)
public class Presenca {
    @Id
    @ManyToOne
    private Chamada chamada;

    @Id
    @ManyToOne
    private Pessoa aluno;

    private Boolean presente;

    public Chamada getChamada() {
        return chamada;
    }

    public void setChamada(Chamada chamada) {
        this.chamada = chamada;
    }

    public Pessoa getAluno() {
        return aluno;
    }

    public void setAluno(Pessoa aluno) {
        this.aluno = aluno;
    }

    public Boolean getPresente() {
        return presente;
    }

    public void setPresente(Boolean presente) {
        this.presente = presente;
    }
}
