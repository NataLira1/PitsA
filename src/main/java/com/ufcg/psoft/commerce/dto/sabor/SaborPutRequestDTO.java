package com.ufcg.psoft.commerce.dto.sabor;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class SaborPutRequestDTO {

    @NotBlank(message = "CAMPO NOME OBRIGATORIO")
    @JsonProperty("nome")
    private String nome;

    @NotBlank(message = "CAMPO TIPO OBRIGATORIO")
    @JsonProperty("tipo")
    private String tipo;

    @NotNull
    @JsonProperty("valorMedia")
    private double valorMedia;

    @NotNull
    @JsonProperty("valorGrande")
    private double valorGrande;

    @JsonProperty("disponvel")
    private boolean disponivel;
}