package com.ufcg.psoft.commerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "clientes")
public class Cliente {

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
    @ManyToMany
    private Set<Sabor> interesse;
}
