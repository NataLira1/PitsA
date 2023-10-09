package com.ufcg.psoft.commerce.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sabores")
public class Sabor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pk_id_sabor", nullable = false)
    private Long id;

    @Column(name = "desc_nome", nullable = false)
    @JsonProperty("nome")
    private String nome;

    @Column(name = "desc_tipo", nullable = false)
    @JsonProperty("tipo")
    private String tipo;

    @Column(name = "double_valor_media", nullable = false)
    @JsonProperty("valorMedia")
    private double valorMedia;

    @Column(name = "double_valor_grande", nullable = false)
    @JsonProperty("valorGrande")
    private double valorGrande;

    @Column(name = "bool_disponivel", nullable = false)
    @JsonProperty("disponivel")
    private boolean disponivel;

    @Column(name = "clientesInteressados")
    @JsonProperty("clientesInteressados")
    @ManyToMany(mappedBy = "interesse", cascade = CascadeType.ALL)
    private Set<Cliente> clientesInteressados;

//    @JsonProperty("estabelecimento")
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "estabelecimento_pk_id")
    private Estabelecimento estabelecimento;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Sabor other = (Sabor) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        return result;
    }

    

    

    

}