package com.ufcg.psoft.commerce.dto.sabor;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaborResponseDTO {

    @JsonProperty("id")
    private Long id;

    
    @JsonProperty("nome")
    private String nome;


    @JsonProperty("tipo")
    private String tipo;


    @JsonProperty("valorMedia")
    private double valorMedia;


    @JsonProperty("valorGrande")
    private double valorGrande;


    @JsonProperty("disponivel")
    private boolean disponivel;
}
