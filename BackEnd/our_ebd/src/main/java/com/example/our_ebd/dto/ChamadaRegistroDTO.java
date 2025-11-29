package com.example.our_ebd.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ChamadaRegistroDTO {
    private Long turmaId;
    private LocalDate dataChamada;
    private String statusChamada;
    private BigDecimal valorOferta;
    private Integer qtdVisitantes;
    private List<PresencaDTO> presencas;

    // Getters e Setters
    public Long getTurmaId() {
        return turmaId;
    }

    public void setTurmaId(Long turmaId) {
        this.turmaId = turmaId;
    }

    public LocalDate getDataChamada() {
        return dataChamada;
    }

    public void setDataChamada(LocalDate dataChamada) {
        this.dataChamada = dataChamada;
    }

    public String getStatusChamada() {
        return statusChamada;
    }

    public void setStatusChamada(String statusChamada) {
        this.statusChamada = statusChamada;
    }

    public BigDecimal getValorOferta() {
        return valorOferta;
    }

    public void setValorOferta(BigDecimal valorOferta) {
        this.valorOferta = valorOferta;
    }

    public Integer getQtdVisitantes() {
        return qtdVisitantes;
    }

    public void setQtdVisitantes(Integer qtdVisitantes) {
        this.qtdVisitantes = qtdVisitantes;
    }

    public List<PresencaDTO> getPresencas() {
        return presencas;
    }

    public void setPresencas(List<PresencaDTO> presencas) {
        this.presencas = presencas;
    }
}