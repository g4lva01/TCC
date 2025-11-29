package com.example.our_ebd.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Turma {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer limiteDeIdade;

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL)
    private List<Pessoa> alunos = new ArrayList<>();

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlunoTurma> matriculas = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<AlunoTurma> getMatriculas() { return matriculas; }

    public void setMatriculas(List<AlunoTurma> matriculas) { this.matriculas = matriculas; }

    public List<Pessoa> getAlunos() {
        return alunos != null ? alunos : List.of();
    }
}
