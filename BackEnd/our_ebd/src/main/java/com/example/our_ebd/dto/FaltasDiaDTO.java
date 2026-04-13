package com.example.our_ebd.dto;

import java.time.LocalDate;

public class FaltasDiaDTO {
    private LocalDate data;
    private Long totalFaltas;

    public FaltasDiaDTO(LocalDate data, Long totalFaltas) {
        this.data = data;
        this.totalFaltas = totalFaltas;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Long getTotalFaltas() {
        return totalFaltas;
    }

    public void setTotalFaltas(Long totalFaltas) {
        this.totalFaltas = totalFaltas;
    }
}
