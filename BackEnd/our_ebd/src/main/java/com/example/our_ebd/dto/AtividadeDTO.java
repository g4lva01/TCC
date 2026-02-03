package com.example.our_ebd.dto;

import com.example.our_ebd.model.Atividade;

import java.time.LocalDate;

public class AtividadeDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDate dataPublicacao;
    private String turmaNome;
    private Long turmaId;
    private Integer numeroLicao;

    public AtividadeDTO(Atividade atividade) {
        this.id = atividade.getId();
        this.titulo = atividade.getTitulo();
        this.descricao = atividade.getDescricao();
        this.dataPublicacao = atividade.getDataPublicacao();
        this.turmaNome = atividade.getTurma().getNome();
        this.turmaId = atividade.getTurma().getId();
        this.numeroLicao = atividade.getNumeroLicao();
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    public String getTurmaNome() {
        return turmaNome;
    }

    public Long getTurmaId() {
        return turmaId;
    }

    public Integer getNumeroLicao() {
        return numeroLicao;
    }
}
