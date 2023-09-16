package com.ufcg.psoft.commerce.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "estabelecimento")
public class Estabelecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonProperty("id")
    @Column(name = "pk_id_endereco", unique = true, nullable = false)
    private Long id;

    @JsonProperty("codigoDeAcesso")
    @Column(name = "estabelecimento_codigoAcesso", nullable = false)
    private String codigoAcesso;

    @Column(name = "estabelecimento_entregadores", nullable = true)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Entregador> entregadores;

    @Column(name = "estabelecimento_cardapio", nullable = true)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Sabor> cardapio;


    /*@JsonProperty("pedidos")
    @Column(name = "estabelecimento_pedidos", nullable = true)
    private Map<Long, List<Pedido>> pedidos;*/


}
