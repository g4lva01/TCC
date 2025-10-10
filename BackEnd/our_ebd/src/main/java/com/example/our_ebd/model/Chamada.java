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
    private Turma turma;

    @ManyToOne
    private Pessoa professor;

    private LocalDate dataChamada;
    private String statusChamada;
    private BigDecimal valorOferta;
    private Integer qtdVisitantes;

    @OneToMany(mappedBy = "chamada", cascade = CascadeType.ALL)
    private List<Presenca> presencas;
}
