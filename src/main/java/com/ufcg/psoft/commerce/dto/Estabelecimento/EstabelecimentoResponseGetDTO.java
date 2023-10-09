package com.ufcg.psoft.commerce.dto.Estabelecimento;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.dto.sabor.SaborInteresseResponseDTO;
import com.ufcg.psoft.commerce.models.Entregador;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstabelecimentoResponseGetDTO {

    @JsonProperty("es_codigoDeAcesso")
    private String codigoAcesso;

    @JsonProperty("es_usuario")
    private String usuario;

    @JsonProperty("es_entregadores")
    private Set<Entregador> entregadores;

    @JsonProperty("es_cardapio")
    private Set<SaborInteresseResponseDTO> cardapio;
}
