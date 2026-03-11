package com.example.our_ebd.dto;

import java.time.LocalDate;

public class RelatorioAlunoDTO {
    private LocalDate data;
    private boolean presente;
    private boolean levouBiblia;
    private boolean levouRevista;

    public RelatorioAlunoDTO(LocalDate data, boolean presente, boolean levouBiblia, boolean levouRevista) {
        this.data = data;
        this.presente = presente;
        this.levouBiblia = levouBiblia;
        this.levouRevista = levouRevista;
    }

    public LocalDate getData() {
        return data;
    }

    public boolean isPresente() {
        return presente;
    }

    public boolean isLevouBiblia() {
        return levouBiblia;
    }

    public boolean isLevouRevista() {
        return levouRevista;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }

    public void setLevouBiblia(boolean levouBiblia) {
        this.levouBiblia = levouBiblia;
    }

    public void setLevouRevista(boolean levouRevista) {
        this.levouRevista = levouRevista;
    }
}
