package com.example.our_ebd.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ChamadaRespostaDTO(
        Long id,
        LocalDate dataChamada,
        String statusChamada,
        BigDecimal valorOferta,
        Integer qtdVisitantes
) {}
