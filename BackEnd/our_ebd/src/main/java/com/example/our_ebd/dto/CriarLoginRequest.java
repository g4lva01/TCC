package com.example.our_ebd.dto;

import java.time.LocalDate;

public class CriarLoginRequest {
    private String nome;
    private LocalDate dtNascimento;
    private Integer matricula;
    private String senha;
    private String cfSenha;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(LocalDate dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCfSenha() {
        return cfSenha;
    }

    public void setCfSenha(String cfSenha) {
        this.cfSenha = cfSenha;
    }
}
