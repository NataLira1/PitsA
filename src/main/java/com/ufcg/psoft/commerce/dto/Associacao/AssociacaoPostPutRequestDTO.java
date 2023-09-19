package com.ufcg.psoft.commerce.dto.Associacao;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssociacaoPostPutRequestDTO {

    @NotBlank
    @JsonProperty("entregadorId")
    private Long entregadorId;

    @NotBlank
    @JsonProperty("estabelecimentoId")
    private Long estabelecimentoId;

    @NotBlank
    @JsonProperty("codigoAcessoEntregador")
    private String codigoAcessoEntregador;

    @JsonProperty("status")
    private boolean status;

}
