package com.ufcg.psoft.commerce.models;


import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.service.notificacao.PedidoAdapter;
import com.ufcg.psoft.commerce.service.notificacao.PedidoEvent;
import com.ufcg.psoft.commerce.utils.EmailSender;

import jakarta.annotation.Resource;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "estabelecimentos")
@Table(name = "estabelecimento")
public class Estabelecimento extends PedidoAdapter {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonProperty("id")
    @Column(name = "pk_id_endereco", unique = true, nullable = false)
    private Long id;

    @JsonProperty("usuario")
    @Column(name = "estabelecimento_usuario", nullable = true)
    private String usuario;

    @JsonProperty("codigoDeAcesso")
    @Column(name = "estabelecimento_codigoAcesso", nullable = false)
    private String codigoAcesso;

    @Column(name = "estabelecimento_entregadores", nullable = true)
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<Entregador> entregadores;

    //@JsonIgnore
    @Column(name = "estabelecimento_cardapio", nullable = true)
    @OneToMany(mappedBy = "estabelecimento", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<Sabor> cardapio;

    public Set<Sabor> saboresDisponiveis(){
        Set<Sabor> saboresDisponiveis = new HashSet<>();
        for (Sabor sabor : cardapio){
            if(sabor.isDisponivel()){
                saboresDisponiveis.add(sabor);
            }
        }
        return saboresDisponiveis;
    }

    /*
    @Column(name = "estabelecimento_pedidos", nullable = true)
    private Map<Long, List<Pedido>> pedidos;*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estabelecimento that = (Estabelecimento) o;
        return Objects.equals(codigoAcesso, that.codigoAcesso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoAcesso);
    }

    @Override
    public void notificaPedidoEmRota(PedidoEvent pedido) {}

    @Override
    public void notificaPedidoEntregue(PedidoEvent pedido) {
        String mensagem = "Pedido #" + pedido.getPedido_id() + " foi entregue!";
        System.out.println(mensagem);
    }

}
