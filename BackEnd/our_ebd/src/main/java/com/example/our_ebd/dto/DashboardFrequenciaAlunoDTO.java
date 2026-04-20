package com.example.our_ebd.dto;

public class DashboardFrequenciaAlunoDTO {
    private String nomeAluno;
    private String nomeTurma;
    private long presencas;
    private long faltas;
    private int domingosNoTrimestre;
    private double percentualPresenca;
    private double mediaTurma;

    public DashboardFrequenciaAlunoDTO (String nomeAluno, String nomeTurma, long presencas, long faltas, int domingosNoTrimestre, double percentualPresenca, double mediaTurma) {
        this.nomeAluno = nomeAluno;
        this.nomeTurma = nomeTurma;
        this.presencas = presencas;
        this.faltas = faltas;
        this.domingosNoTrimestre = domingosNoTrimestre;
        this.percentualPresenca = percentualPresenca;
        this.mediaTurma = mediaTurma;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getNomeTurma() {
        return nomeTurma;
    }

    public void setNomeTurma(String nomeTurma) {
        this.nomeTurma = nomeTurma;
    }

    public long getPresencas() {
        return presencas;
    }

    public void setPresencas(long presencas) {
        this.presencas = presencas;
    }

    public long getFaltas() {
        return faltas;
    }

    public void setFaltas(long faltas) {
        this.faltas = faltas;
    }

    public int getDomingosNoTrimestre() {
        return domingosNoTrimestre;
    }

    public void setDomingosNoTrimestre(int domingosNoTrimestre) {
        this.domingosNoTrimestre = domingosNoTrimestre;
    }

    public double getPercentualPresenca() {
        return percentualPresenca;
    }

    public void setPercentualPresenca(double percentualPresenca) {
        this.percentualPresenca = percentualPresenca;
    }

    public double getMediaTurma() {
        return mediaTurma;
    }

    public void setMediaTurma(double mediaTurma) {
        this.mediaTurma = mediaTurma;
    }
}
