package com.ufcg.psoft.commerce.dto.Entregador;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EntregadorDeleteRequestDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("codigoAcesso")
    private String codigoAcesso;
}
