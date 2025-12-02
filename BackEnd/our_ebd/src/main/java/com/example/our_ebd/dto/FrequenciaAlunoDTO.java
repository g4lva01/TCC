package com.example.our_ebd.dto;

public record FrequenciaAlunoDTO(
        String nomeAluno,
        String nomeTurma,
        long presencas,
        int domingosNoTrimestre,
        double percentualPresenca
) {}
