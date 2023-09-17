package com.ufcg.psoft.commerce.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "double_preco_medio", nullable = false)
    @JsonProperty("precoM")
    private double precoM;

    @Column(name = "double_preco_grande", nullable = false)
    @JsonProperty("precoG")
    private double precoG;

    @Column(name = "bool_disponivel", nullable = false)
    @JsonProperty("disponivel")
    private boolean disponivel;

    @ManyToOne()//cascade = CascadeType.PERSIST)
    @JsonProperty("estabelecimento")
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
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    

    

}