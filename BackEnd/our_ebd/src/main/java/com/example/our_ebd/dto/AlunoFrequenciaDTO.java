package com.example.our_ebd.dto;

public class AlunoFrequenciaDTO {
    private Long alunoId;
    private String nomeAluno;
    private Long presencas;
    private Long faltas;

    public AlunoFrequenciaDTO(Long alunoId, String nomeAluno, Long presencas, Long faltas) {
        this.alunoId = alunoId;
        this.nomeAluno = nomeAluno;
        this.presencas = presencas;
        this.faltas = faltas;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public Long getPresencas() {
        return presencas;
    }

    public void setPresencas(Long presencas) {
        this.presencas = presencas;
    }

    public Long getFaltas() {
        return faltas;
    }

    public void setFaltas(Long faltas) {
        this.faltas = faltas;
    }
}