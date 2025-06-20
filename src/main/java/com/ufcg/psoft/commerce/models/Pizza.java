package com.ufcg.psoft.commerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "pizza")
@Table(name = "pizza")
public class Pizza {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "pk_id_pizza", nullable = false)
	private Long id;

	@JsonProperty("sabor1")
	@OneToOne
//        @JoinColumn(name = "pk_id_sabor")
	private Sabor sabor1;

	@JsonProperty("sabor2")
	@OneToOne
	private Sabor sabor2;

	@Column(name = "desc_tamanho", nullable = false)
	@JsonProperty("tamanho")
	private String tamanho;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "pk_id_pedido")
	private Pedido pedido;

	public double calcularPrecoTotal() {
		double precoTotal = sabor1.getValorMedia();
		return precoTotal;
	}
}