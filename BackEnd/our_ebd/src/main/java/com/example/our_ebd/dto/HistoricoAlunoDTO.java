package com.example.our_ebd.dto;

import com.example.our_ebd.model.Presenca;

public class HistoricoAlunoDTO {
    private String nomeTurma;
    private long presencas;
    private long qtdBiblia;
    private long qtdRevista;
    private long ausencias;

    public HistoricoAlunoDTO(String nomeTurma, long presencas, long qtdBiblia, long qtdRevista, long ausencias) {
        this.nomeTurma = nomeTurma;
        this.presencas = presencas;
        this.qtdBiblia = qtdBiblia;
        this.qtdRevista = qtdRevista;
        this.ausencias = ausencias;
    }

    public String getNomeTurma() {
        return nomeTurma;
    }

    public long getPresencas() {
        return presencas;
    }

    public long getQtdBiblia() {
        return qtdBiblia;
    }

    public long getQtdRevista() {
        return qtdRevista;
    }

    public long getAusencias() {
        return ausencias;
    }

    public void setNomeTurma(String nomeTurma) {
        this.nomeTurma = nomeTurma;
    }

    public void setPresencas(long presencas) {
        this.presencas = presencas;
    }

    public void setQtdBiblia(long qtdBiblia) {
        this.qtdBiblia = qtdBiblia;
    }

    public void setQtdRevista(long qtdRevista) {
        this.qtdRevista = qtdRevista;
    }

    public void setAusencias(long ausencias) {
        this.ausencias = ausencias;
    }
}
