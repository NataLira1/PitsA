package com.ufcg.psoft.commerce.dto.pedido;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.models.Cliente;
import com.ufcg.psoft.commerce.models.Entregador;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.models.Pizza;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("preco")
    private double preco;

    @JsonProperty("status")
    private String status;

    @JsonProperty("enderecoEntrega")
    private String enderecoEntrega;

    @JsonProperty("cliente")
    private Cliente cliente;

    @JsonProperty("estabelecimento")
    private Estabelecimento estabelecimento;

    @JsonProperty("entregadorId")
    private Entregador entregador;

    @JsonProperty("pizzas")
    private List<Pizza> pizzas;

}