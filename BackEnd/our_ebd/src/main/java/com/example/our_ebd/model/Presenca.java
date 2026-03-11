package com.example.our_ebd.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;

@Entity
@IdClass(PresencaId.class)
public class Presenca {
    @Id
    @ManyToOne
    private Chamada chamada;

    @Id
    @ManyToOne
    private Pessoa aluno;

    private Boolean presente;

    private Boolean levouBiblia;

    private Boolean levouRevista;

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

    public Boolean getLevouBiblia() {
        return levouBiblia;
    }

    public void setLevouBiblia(Boolean levouBiblia) {
        this.levouBiblia = levouBiblia;
    }

    public Boolean getLevouRevista() {
        return levouRevista;
    }

    public void setLevouRevista(Boolean levouRevista) {
        this.levouRevista = levouRevista;
    }
}
