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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @Column(name = "pk_id_pedido")
    private Long id;

    @Column(name = "desc_preco", nullable = false)
    @JsonProperty("preco")
    private double preco;

    @Column(name = "desc_enderecoEntrega")
    @JsonProperty("enderecoEntrega")
    private String enderecoEntrega;

    @Column(name = "desc_status")
    @JsonProperty
    private String status;

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

    
    public double calcularPrecoPedido() {
    	double precoPedido = 0;
    	
    	for (Pizza p : pizzas) {
    		precoPedido += p.calcularPrecoTotal();
    	}
    	
    	return precoPedido;
    }

}