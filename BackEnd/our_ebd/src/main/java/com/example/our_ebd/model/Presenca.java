package com.example.our_ebd.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;

@Entity
@IdClass(Presenca.class)
public class Presenca {
    @Id
    @ManyToOne
    private Chamada chamada;

    @Id
    @ManyToOne
    private Pessoa aluno;

    private Boolean presente;
}
