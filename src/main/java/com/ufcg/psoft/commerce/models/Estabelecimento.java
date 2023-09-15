package com.ufcg.psoft.commerce.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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

    @JsonProperty("usuario")
    @Column(name = "estabelecimento_usuario", unique = true, nullable = false)
    private String usuario;

    @JsonProperty("codigoDeAcesso")
    @Column(name = "estabelecimento_codigoAcesso", nullable = false)
    private String codigoAcesso;

    /*@JsonProperty("funcionarios")
    @Column(name = "estabelecimento_funcionarios", nullable = true)
    private Set<Entregador> funcionarios;*/

    /*@JsonProperty("Cardapio")
    @Column(name = "estabelecimento_cardapio", nullable = false)
    private Set<Sabor> cardapio;*/


    /*@JsonProperty("pedidos")
    @Column(name = "estabelecimento_pedidos", nullable = true)
    private Map<Long, List<Pedido>> pedidos;*/


}
