package com.ufcg.psoft.commerce.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "pedidos")
@Table(name = "pedido")

public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pk_id_pedido", nullable = false)
    private Long id;

    @Column(name = "desc_preco", nullable = false)
    @JsonProperty("preco")
    private double preco;

    @Column(name = "desc_enderecoEntrega", nullable = true)
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
    @OneToMany(mappedBy = "pedido", cascade = {CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Pizza> pizzas;


}
