package com.ufcg.psoft.commerce.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pk_id_pedido", nullable = false)
    private Long id;

    @Column(name = "desc_preco", nullable = false)
    @JsonProperty("preco")
    private double preco;

    @Column(name = "desc_enderecoEntrega", nullable = false)
    @JsonProperty("enderecoEntrega")
    private String enderecoEntrega;

    @Column(name = "fk_id_cliente", nullable = false)
    @JsonProperty("clienteId")
    private Long clienteId;

    @Column(name = "fk_id_estabelecimento", nullable = false)
    @JsonProperty("estabelecimentoId")
    private Long estabelecimentoId;

    @Column(name = "fk_id_entregador", nullable = false)
    @JsonProperty("entregadorId")
    private Long entregadorId;

    @Column(name = "list_pizzas", nullable = false)
    @JsonProperty("pizzas")
    private List<Pizza> pizzas;

}
