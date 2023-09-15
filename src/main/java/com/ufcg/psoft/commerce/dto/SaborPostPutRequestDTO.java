package com.ufcg.psoft.commerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
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

    @NotBlank
    @JsonProperty("nome")
    private String nome;

    @NotBlank
    @JsonProperty("tipo")
    private String tipo;

    @NotBlank
    @JsonProperty("valorMedia")
    private double valorMedia;

   @NotBlank
    @JsonProperty("valorGrande")
    private double valorGrande;
}
