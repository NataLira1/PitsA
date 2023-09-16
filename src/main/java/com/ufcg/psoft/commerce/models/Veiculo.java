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
@Embeddable
public class Veiculo {
    @Column(name = "placa_veiculo")
    private String placa;

    @Column(name = "tipo_veiculo")
    private String tipo;
    
    @Column(name = "cor_veiculo")
    private String cor;
}
