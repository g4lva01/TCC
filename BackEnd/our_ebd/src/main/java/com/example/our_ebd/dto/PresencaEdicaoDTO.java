package com.example.our_ebd.dto;

public record PresencaEdicaoDTO(
        Long alunoId,
        String nome,
        boolean presente,
        boolean levouBiblia,
        boolean levouRevista
) {}
