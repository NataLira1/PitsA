package com.ufcg.psoft.commerce.dto.Estabelecimento;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.models.Entregador;
import com.ufcg.psoft.commerce.models.Sabor;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EstabelecimentoPostPutRequestDTO {

    @NotBlank(message = "Campo de codigo de acesso obrigatorio")
    private String codigoAcesso;


    private String usuario;

    private Set<Entregador> entregadores;

    @JsonProperty("cardapio")
    private Set<Sabor> cardapio;


}
