package com.ufcg.psoft.commerce.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "associacao")
public class Associacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pk_id_asssociacao")
    @JsonProperty("id")
    private Long id;

    @Column(name = "id_entregador")
    @JsonProperty("entregadorId")
    private Long entregadorId;

    @Column(name = "id_estabelecimento")
    @JsonProperty("estabelecimentoId")
    private Long estabelecimentoId;

}
