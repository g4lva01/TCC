package com.example.our_ebd.model;

import jakarta.persistence.*;

@Entity
public class PessoaPerfil {

    @EmbeddedId
    private PessoaPerfilId id;

    @ManyToOne
    @MapsId("pessoa")
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    @ManyToOne
    @MapsId("perfil")
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;

    public PessoaPerfil() {}

    public PessoaPerfil(Pessoa pessoa, Perfil perfil) {
        this.pessoa = pessoa;
        this.perfil = perfil;
        this.id = new PessoaPerfilId(pessoa.getId(), perfil.getId());
    }

    // Getters e setters
    public PessoaPerfilId getId() { return id; }
    public void setId(PessoaPerfilId id) { this.id = id; }

    public Pessoa getPessoa() { return pessoa; }
    public void setPessoa(Pessoa pessoa) { this.pessoa = pessoa; }

    public Perfil getPerfil() { return perfil; }
    public void setPerfil(Perfil perfil) { this.perfil = perfil; }
}
