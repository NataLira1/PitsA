package com.ufcg.psoft.commerce.dto.Estabelecimento;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EstabelecimentoPatchCodigoDTO {

    @NotBlank(message = "Campo de Codigo de acesso obrigatorio")
    String codigo;
}
