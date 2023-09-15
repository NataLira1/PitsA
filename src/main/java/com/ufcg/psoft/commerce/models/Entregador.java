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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Entregador {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pk_entregador_id")
    private Long id;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "codigoAcesso")
    private String codigoAcesso;
    
    @Column(name = "veiculo")
    private Veiculo veiculo;

    @Column(name = "nome")
    private String nome;

    @Column(name = "estabelecimento")
    private Estabelecimento estabelecimento;

    @Column(name = "disponivel")
    private String disponivel;
}
