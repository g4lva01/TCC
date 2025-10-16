package com.example.our_ebd.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Chamada {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private Pessoa professor;

    private LocalDate dataChamada;
    private String statusChamada;
    private BigDecimal valorOferta;
    private Integer qtdVisitantes;

    @OneToMany(mappedBy = "chamada", cascade = CascadeType.ALL)
    private List<Presenca> presencas;

    public Long getId() {
        return id;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Pessoa getProfessor() {
        return professor;
    }

    public void setProfessor(Pessoa professor) {
        this.professor = professor;
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

    public List<Presenca> getPresencas() {
        return presencas;
    }

    public void setPresencas(List<Presenca> presencas) {
        this.presencas = presencas;
    }
}
