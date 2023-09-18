package com.ufcg.psoft.commerce.dto.Entregador;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.models.Veiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EntregadorPostPutRequestDTO {
    @NotBlank
    @NotNull
    @JsonProperty("nome")
    private String nome;

    @NotNull
    @JsonProperty("veiculo")
    private Veiculo veiculo;

    @NotBlank
    @NotNull
    @JsonProperty("codigoAcesso")
    private String codigoAcesso;
}
