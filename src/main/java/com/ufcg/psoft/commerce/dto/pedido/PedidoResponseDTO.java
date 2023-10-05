package com.ufcg.psoft.commerce.dto.pedido;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.models.Pizza;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class PedidoResponseDTO {

    @JsonProperty("preco")
    private double preco;

    @JsonProperty("enderecoEntrega")
    private String enderecoEntrega;

    @JsonProperty("clienteId")
    private Long clienteId;

    @JsonProperty("estabelecimentoId")
    private Long estabelecimentoId;

    @JsonProperty("entregadorId")
    private Long entregadorId;

    @JsonProperty("pizzas")
    private List<Pizza> pizzas;

}
