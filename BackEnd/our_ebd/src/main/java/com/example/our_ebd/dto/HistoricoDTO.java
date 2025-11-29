package com.example.our_ebd.dto;

import java.time.LocalDate;

public record HistoricoDTO(
        LocalDate data,
        long presentes,
        long levouBiblia,
        long revistas
) {}
