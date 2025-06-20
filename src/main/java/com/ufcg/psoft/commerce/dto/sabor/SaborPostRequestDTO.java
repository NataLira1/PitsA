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
public class SaborPostRequestDTO {
    @NotBlank(message = "CAMPO NOME OBRIGATORIO")
    @NotNull(message = "CAMPO NOME NÃO PODE SER NULL")
    @JsonProperty("nome")
    private String nome;

    @NotBlank(message = "CAMPO TIPO OBRIGATORIO")
    @NotNull(message = "CAMPO TIPO NÃO PODE SER NULL")
    @JsonProperty("tipo")
    private String tipo;

    @NotNull
    @JsonProperty("valorMedia")
    private double valorMedia;

    @NotNull
    @JsonProperty("valorGrande")
    private double valorGrande;
}
