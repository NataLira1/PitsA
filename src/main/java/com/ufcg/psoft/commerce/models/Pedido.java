package com.ufcg.psoft.commerce.models;

import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.service.notificacao.PedidoEvent;

import com.ufcg.psoft.commerce.service.notificacao.PedidoEvent;
import com.ufcg.psoft.commerce.service.notificacao.PedidoObserver;
import jakarta.persistence.*;
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
public class Pedido{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pk_id_pedido")
    private Long id;

    @Column(name = "desc_preco", nullable = false)
    @JsonProperty("preco")
    private double preco;

    @Column(name = "desc_enderecoEntrega")
    @JsonProperty("enderecoEntrega")
    private String enderecoEntrega;

    @Column(name = "desc_status")
    @JsonProperty("status")
    private String status;

    @Column(name = "desc_status_pagamento")
    @JsonProperty("statusPagamento")
    private boolean statusPagamento;

    @JsonProperty("cliente")
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @JsonProperty("estabelecimento")
    @ManyToOne
    @JoinColumn(name = "estabelecimento_id")
    private Estabelecimento estabelecimento;

    @JsonProperty("entregador")
    @ManyToOne
    @JoinColumn(name = "entregador_id")
    private Entregador entregador;

    @JsonProperty("pizzas")
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Pizza> pizzas;

    @JsonProperty("formaPagamento")
    @Embedded
    private FormaDePagamento formaDePagamento;

    public void setStatus(String status){
        this.status = status;

        if(status.equals("Pedido entregue")) {
            estabelecimento.notificaPedidoEntregue(PedidoEvent.builder().pedido_id(this.id).build());
        }

        else if(status.equals("Pedido em rota")){
            PedidoObserver observer = new PedidoObserver();
            observer.adicionaListener(this.cliente);
            cliente.notificaPedidoEmRota(PedidoEvent.builder().entregador(this.entregador).build());
        }
    }

    public double calcularPrecoPedido() {
        double precoPedido = 0;

        for (Pizza p : pizzas) {
            precoPedido += p.calcularPrecoTotal();
        }

        return precoPedido;
    }
}