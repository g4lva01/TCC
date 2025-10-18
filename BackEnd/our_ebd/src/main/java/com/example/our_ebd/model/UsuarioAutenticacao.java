package com.example.our_ebd.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class UsuarioAutenticacao {
    @Id
    private Long pessoaId;

    @OneToOne
    @MapsId
    private Pessoa pessoa;

    @Column(nullable = false)
    private String senha;

    private LocalDate dataUltimoLogin;

    public Long getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Long pessoaId) {
        this.pessoaId = pessoaId;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getDataUltimoLogin() {
        return dataUltimoLogin;
    }

    public void setDataUltimoLogin(LocalDate dataUltimoLogin) {
        this.dataUltimoLogin = dataUltimoLogin;
    }
}
