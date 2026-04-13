package com.example.our_ebd.dto;

public class TurmaFrequenciaDTO {
    private Long turmaId;
    private String nomeTurma;
    private Double mediaPresencas;

    public TurmaFrequenciaDTO(Long turmaId, String nomeTurma, Double mediaPresencas) {
        this.turmaId = turmaId;
        this.nomeTurma = nomeTurma;
        this.mediaPresencas = mediaPresencas;
    }

    public Long getTurmaId() {
        return turmaId;
    }

    public void setTurmaId(Long turmaId) {
        this.turmaId = turmaId;
    }

    public String getNomeTurma() {
        return nomeTurma;
    }

    public void setNomeTurma(String nomeTurma) {
        this.nomeTurma = nomeTurma;
    }

    public Double getMediaPresencas() {
        return mediaPresencas;
    }

    public void setMediaPresencas(Double mediaPresencas) {
        this.mediaPresencas = mediaPresencas;
    }
}
