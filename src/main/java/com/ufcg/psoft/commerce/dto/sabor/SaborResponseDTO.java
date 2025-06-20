package com.ufcg.psoft.commerce.dto.sabor;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.ufcg.psoft.commerce.models.Cliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Set;

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

    @JsonProperty("clientesInteressados")
    private Set<Cliente> clientesInteressados;
}
