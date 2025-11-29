package com.example.our_ebd.dto;

public class PresencaRegistroDTO {
    private Long alunoId;
    private Boolean presente;
    private Boolean levouBiblia;
    private Boolean levouRevista;

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }

    public Boolean getPresente() {
        return presente;
    }

    public void setPresente(Boolean presente) {
        this.presente = presente;
    }

    public Boolean getLevouBiblia() {
        return levouBiblia;
    }

    public void setLevouBiblia(Boolean levouBiblia) {
        this.levouBiblia = levouBiblia;
    }

    public Boolean getLevouRevista() {
        return levouRevista;
    }

    public void setLevouRevista(Boolean levouRevista) {
        this.levouRevista = levouRevista;
    }
}