    package com.ufcg.psoft.commerce.models;

    import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity(name = "pizzas")
    @Table(name = "pizza")
    public class Pizza {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "pk_id_pizza", nullable = false)
        private Long id;


        @Column(name = "desc_sabor1", nullable = false)
        @JsonProperty("sabor1")
        private Sabor sabor1;

        @Column(name = "desc_sabor2", nullable = true)
        @JsonProperty("sabor2")
        private Sabor sabor2;

        @Column(name = "desc_tamanho", nullable = false)
        @JsonProperty("tamanho")
        private String tamanho;

        @Column(name = "desc_valorPizza", nullable = false)
        @JsonProperty("valorPizza")
        private double valorPizza;

        @JsonIgnore
        @ManyToOne
        @JoinColumn(name = "pedido_pk_id")
        private Pedido pedido;

        

    }
