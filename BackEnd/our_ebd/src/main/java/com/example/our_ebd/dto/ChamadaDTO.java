package com.example.our_ebd.dto;

import java.util.List;

public record ChamadaDTO(
        Long turmaId,
        String dataChamada,
        String statusChamada,
        Double valorOferta,
        Integer qtdVisitantes,
        List<PresencaResumoDTO> presencas
) {}