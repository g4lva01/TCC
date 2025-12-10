package com.example.our_ebd.dto;

public record PresencaResumoDTO(
        String nome,
        int matriculados,
        int presentes,
        int ausentes,
        int biblia,
        int revistas,
        int qtdVisitantes,
        double oferta
) {}
