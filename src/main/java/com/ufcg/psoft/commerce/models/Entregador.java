package com.ufcg.psoft.commerce.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Entregador {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pk_entregador_id")
    private Long id;

    @Column(name = "usuario")
    @JsonProperty("usuario")
    private String usuario;

    @JsonProperty("email")
    @Column(name = "desc_email")
    private String email;

    @Column(name = "codigoAcesso")
    @JsonProperty("codigoAcesso")
    private String codigoAcesso;

    @Embedded
    @Column(name = "veiculo")
    @JsonProperty("veiculo")
    private Veiculo veiculo;

    @Column(name = "nome")
    @JsonProperty("nome")
    private String nome;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "estabelecimento")
    @JsonProperty("estabelecimento")
    @JsonIgnore
    private Estabelecimento estabelecimento;

    @Column(name = "disponivel")
    @JsonProperty("disponivel")
    private String disponivel;

    @Column(name = "status")
    @JsonProperty("status")
    private String status;
}
