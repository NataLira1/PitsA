package com.ufcg.psoft.commerce.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "veiculo")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonProperty("id")
    @Column(name = "pk_id_veiculo", unique = true, nullable = false)
    Long id;

    @Column(name = "placa")
    private String placa;

    @Column(name = "tipo")
    private String tipo;
    
    @Column(name = "cor")
    private String cor;
}
