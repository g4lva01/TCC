package com.example.our_ebd.dto;

import java.time.LocalDate;
import java.util.List;

public class AvisoSemChamadaDTO {
    private String nomeTurma;
    private List<LocalDate> diasSemChamada;

    public AvisoSemChamadaDTO(String nomeTurma, List<LocalDate> diasSemChamada) {
        this.nomeTurma = nomeTurma;
        this.diasSemChamada = diasSemChamada;
    }

    public String getNomeTurma() {
        return nomeTurma;
    }

    public void setNomeTurma(String nomeTurma) {
        this.nomeTurma = nomeTurma;
    }

    public List<LocalDate> getDiasSemChamada() {
        return diasSemChamada;
    }

    public void setDiasSemChamada(List<LocalDate> diasSemChamada) {
        this.diasSemChamada = diasSemChamada;
    }
}
