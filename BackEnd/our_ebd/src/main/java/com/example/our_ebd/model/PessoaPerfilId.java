package com.example.our_ebd.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PessoaPerfilId implements Serializable {
    private Long pessoa;
    private Long perfil;

    public PessoaPerfilId() {}

    public PessoaPerfilId(Long pessoa, Long perfil) {
        this.pessoa = pessoa;
        this.perfil = perfil;
    }

    // Getters e setters
    public Long getPessoa() { return pessoa; }
    public void setPessoa(Long pessoa) { this.pessoa = pessoa; }

    public Long getPerfil() { return perfil; }
    public void setPerfil(Long perfil) { this.perfil = perfil; }

    // equals() e hashCode() são obrigatórios para chave composta
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PessoaPerfilId)) return false;
        PessoaPerfilId that = (PessoaPerfilId) o;
        return Objects.equals(pessoa, that.pessoa) &&
                Objects.equals(perfil, that.perfil);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pessoa, perfil);
    }
}