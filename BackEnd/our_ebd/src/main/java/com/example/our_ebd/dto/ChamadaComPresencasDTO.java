package com.example.our_ebd.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ChamadaComPresencasDTO(
        Long id,
        LocalDate dataChamada,
        String statusChamada,
        BigDecimal valorOferta,
        Integer qtdVisitantes,
        List<PresencaResumoDTO> presencas
) {}
