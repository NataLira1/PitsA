package com.ufcg.psoft.commerce.dto.pedido;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.models.Pizza;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoPostPutRequestDTO {

    @NotNull(message = "PRECO OBRIGATORIO")
    @JsonProperty("preco")
    private double preco;

    @NotNull(message = "ENDERECO OBRIGATORIO")
    @NotBlank(message = "ENDERECO OBRIGATORIO")
    @JsonProperty("enderecoEntrega")
    private String enderecoEntrega;

    @NotNull(message = "CLIENTE OBRIGATORIO")
    @JsonProperty("clienteId")
    private Long clienteId;

    @NotNull(message = "ESTABELECIMENTO OBRIGATORIO")
    @JsonProperty("estabelecimentoId")
    private Long estabelecimentoId;

    @NotNull(message = "ENTREGADOR OBRIGATORIO")
    @JsonProperty("entregadorId")
    private Long entregadorId;

    @NotNull(message = "PIZZAS OBRIGATORIO")
    @JsonProperty("pizzas")
    private List<Pizza> pizzas;

}
