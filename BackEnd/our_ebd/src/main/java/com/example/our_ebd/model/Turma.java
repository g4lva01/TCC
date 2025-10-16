package com.example.our_ebd.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Turma {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer limiteDeIdade;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getLimiteDeIdade() {
        return limiteDeIdade;
    }

    public void setLimiteDeIdade(Integer limiteDeIdade) {
        this.limiteDeIdade = limiteDeIdade;
    }
}
