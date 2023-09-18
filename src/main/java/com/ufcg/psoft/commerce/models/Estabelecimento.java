package com.ufcg.psoft.commerce.models;


import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class Estabelecimento {

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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Entregador> entregadores;

    //@JsonIgnore
    @OneToMany(mappedBy = "estabelecimento", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Sabor> cardapio;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Estabelecimento other = (Estabelecimento) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    

    /*
    @Column(name = "estabelecimento_pedidos", nullable = true)
    private Map<Long, List<Pedido>> pedidos;*/


}
