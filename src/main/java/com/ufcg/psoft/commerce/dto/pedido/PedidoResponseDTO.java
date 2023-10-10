package com.ufcg.psoft.commerce.dto.pedido;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.models.*;

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

    @JsonProperty("statusPagamento")
    private boolean statusPagamento;

    @JsonProperty("cliente")
    private Cliente cliente;

    @JsonProperty("estabelecimento")
    private Estabelecimento estabelecimento;

    @JsonProperty("formaPagamento")
    private FormaDePagamento formaDePagamento;

    @JsonProperty("entregador")
    private Entregador entregador;

    @JsonProperty("pizzas")
    private List<Pizza> pizzas;

}