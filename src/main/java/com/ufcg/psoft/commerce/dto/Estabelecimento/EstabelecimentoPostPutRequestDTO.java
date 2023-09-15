package com.ufcg.psoft.commerce.dto.Estabelecimento;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EstabelecimentoPostPutRequestDTO {

    @NotBlank(message = "Campo de usuario obrigatorio")
    String usuario;

    @NotBlank(message = "Campo de codigo de acesso obrigatorio")
    String codigoAcesso;


}
