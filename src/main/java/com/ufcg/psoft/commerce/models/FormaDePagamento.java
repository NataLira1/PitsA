package com.ufcg.psoft.commerce.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.util.TipoPagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormaDePagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @JsonProperty("forma_pagamento")
    private TipoPagamento forma;
}
