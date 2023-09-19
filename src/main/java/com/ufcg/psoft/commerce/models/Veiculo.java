package com.ufcg.psoft.commerce.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank
    @NotNull
    @Column(name = "placa_veiculo")
    private String placa;

    @NotBlank
    @NotNull
    @Pattern(regexp = "^(carro|moto)$")
    @Column(name = "tipo_veiculo")
    private String tipo;

    @NotBlank
    @NotNull
    @Column(name = "cor_veiculo")
    private String cor;
}