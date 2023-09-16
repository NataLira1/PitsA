package com.ufcg.psoft.commerce.dto.Estabelecimento;

import com.ufcg.psoft.commerce.models.Entregador;
import com.ufcg.psoft.commerce.models.Sabor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EstabelecimentoPostPutRequestDTO {

    @NotBlank(message = "Campo de codigo de acesso obrigatorio")
    private String codigoAcesso;


    private Set<Entregador> entregadores;


    private Set<Sabor> cardapio;


}
