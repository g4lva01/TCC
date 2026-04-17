package com.example.our_ebd.dto;

import java.math.BigDecimal;

public class AlunoFrequenciaDTO {
    private Integer alunoId;
    private String nomeAluno;
    private BigDecimal presencas;
    private BigDecimal faltas;

    public AlunoFrequenciaDTO(Integer alunoId, String nomeAluno, BigDecimal presencas, BigDecimal faltas) {
        this.alunoId = alunoId;
        this.nomeAluno = nomeAluno;
        this.presencas = presencas;
        this.faltas = faltas;
    }

    public Integer getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Integer alunoId) {
        this.alunoId = alunoId;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public BigDecimal getPresencas() {
        return presencas;
    }

    public void setPresencas(BigDecimal presencas) {
        this.presencas = presencas;
    }

    public BigDecimal getFaltas() {
        return faltas;
    }

    public void setFaltas(BigDecimal faltas) {
        this.faltas = faltas;
    }
}