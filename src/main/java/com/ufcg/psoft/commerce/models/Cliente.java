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
@Entity(name = "clientes")
public class Cliente {

    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pk_id_cliente")
    private Long id;

    @JsonProperty("nomeCompleto")
    @Column(nullable = false, name = "desc_nomeCompleto")
    private String nomeCompleto;

    @JsonProperty("usuario")
    @Column(nullable = false, name = "desc_usuario")
    private String usurio;

    @JsonProperty("chaveAcesso")
    @Column(nullable = false, name = "desc_chaveAcesso")
    private String chaveAcesso;

    @JsonProperty
    @Column(nullable = false, name = "desc_endereco")
    private String endereco;

    @JsonProperty
    @Column(nullable = false, name = "desc_pedidos")
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;



}
