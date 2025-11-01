package com.example.our_ebd.dto;

import java.util.List;

public class AtualizarPerfisRequest {
    private Long pessoaId;
    private List<Long> perfilIds;

    public Long getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Long pessoaId) {
        this.pessoaId = pessoaId;
    }

    public List<Long> getPerfilIds() {
        return perfilIds;
    }

    public void setPerfilIds(List<Long> perfilIds) {
        this.perfilIds = perfilIds;
    }
}
