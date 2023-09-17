package com.ufcg.psoft.commerce.dto.sabor;

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
public class SaborPostPutRequestDTO {

    @NotBlank(message = "CAMPO NOME OBRIGATORIO")
    @JsonProperty("nome")
    private String nome;

    @NotBlank(message = "CAMPO TIPO OBRIGATORIO")
    @JsonProperty("tipo")
    private String tipo;

    @JsonProperty("precoM")
    private double precoM;

    @JsonProperty("precoG")
    private double precoG;

    @JsonProperty("disponvel")
    private boolean disponivel;
}