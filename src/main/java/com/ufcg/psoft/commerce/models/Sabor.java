package com.ufcg.psoft.commerce.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sabor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pk_id_sabor")
    private Long id;

    @Column(name = "desc_nome")
    @JsonProperty("nome")
    private String nome;

    @Column(name = "desc_tipo")
    @JsonProperty("tipo")
    private String tipo;

    @Column(name = "valorMedia")
    @JsonProperty("valorMedia")
    private double valorMedia;

    @Column(name = "valorGrande")
    @JsonProperty("valorGrande")
    private double valorGrande;

    @Column(name = "bool_disponivel")
    @JsonProperty("disponivel")
    private boolean disponivel;
}
