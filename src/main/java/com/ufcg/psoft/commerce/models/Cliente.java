package com.ufcg.psoft.commerce.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.service.notificacao.PedidoAdapter;
import com.ufcg.psoft.commerce.service.notificacao.PedidoEvent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "clientes")
public class Cliente extends PedidoAdapter {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pk_id_cliente")
    private Long id;

    @JsonProperty("nomeCompleto")
    @Column(nullable = false, name = "desc_nomeCompleto")
    private String nomeCompleto;

    @JsonProperty("usuario")
    @Column(nullable = false, name = "desc_usuario")
    private String usuario;

    @JsonProperty("codigoAcesso")
    //@JsonIgnore
    @Column(nullable = false, name = "desc_codigoAcesso")
    private String codigoAcesso;

    @JsonProperty("endereco")
    @Column(nullable = false, name = "desc_endereco")
    private String endereco;

    @JsonProperty("interesse")
    @Column(name = "interesses")
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "interesses_cardapio",
        joinColumns = @JoinColumn(name = "cliente_fk"),
            inverseJoinColumns = @JoinColumn(name = "sabor_fk")
    )
    private Set<Sabor> interesse;

    @Override
    public void notificaPedidoEmRota(PedidoEvent pedido) {
        System.out.println(pedido.getEntregador());
        System.out.println(pedido.getEntregador().getVeiculo());
    }

}
