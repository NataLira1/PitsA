package com.ufcg.psoft.commerce.dto.sabor;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.dto.Cliente.ClienteResponseDTO;
import com.ufcg.psoft.commerce.models.Cliente;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaborInteresseResponseDTO {

    @JsonProperty("sabor_id")
    Long id;

    @JsonProperty("sabor_nome")
    String nome;

    @JsonProperty("sabor_tipo")
    String tipo;

    @JsonProperty("sabor_valorMedia")
    private double valorMedia;

    @JsonProperty("sabor_valorGrande")
    private double valorGrande;

    @JsonProperty("sabor_disponivel")
    private boolean disponivel;

    @JsonProperty("sabor_clientesInteressados")
    private Set<ClienteResponseDTO> clientesInteressados;


}
