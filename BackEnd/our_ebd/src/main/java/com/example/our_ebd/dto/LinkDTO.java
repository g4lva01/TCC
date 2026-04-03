package com.example.our_ebd.dto;

import com.example.our_ebd.model.AtividadeLink;

public class LinkDTO {
    private String url;
    private String descricao;

    public LinkDTO(AtividadeLink link) {
        this.url = link.getUrl();
        this.descricao = link.getDescricao();
    }

    public String getUrl() {
        return url;
    }

    public String getDescricao() {
        return descricao;
    }
}
