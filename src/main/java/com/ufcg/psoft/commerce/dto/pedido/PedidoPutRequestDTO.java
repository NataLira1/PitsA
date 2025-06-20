package com.ufcg.psoft.commerce.dto.pedido;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.models.Pizza;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoPutRequestDTO {
    
    @NotBlank(message = "ENDERECO OBRIGATORIO")
    @JsonProperty("enderecoEntrega")
    private String enderecoEntrega;

    @NotBlank(message = "STATUS OBRIGATORIO")
    @JsonProperty("status")
    private String status;

    @NotNull(message = "PIZZAS OBRIGATORIO")
    @JsonProperty("pizzas")
    private List<Pizza> pizzas;
}
