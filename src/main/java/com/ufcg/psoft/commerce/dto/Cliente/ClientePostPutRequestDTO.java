package com.ufcg.psoft.commerce.dto.Cliente;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientePostPutRequestDTO {

    @JsonProperty("nome")
    @NotBlank(message = "NOME COMPLETO OBRIGATORIO")
    private String nomeCompleto;

    @JsonProperty("usuario")
    @NotBlank(message = "USUARIO OBRIGATORIO")
    private String usuario;

    @JsonProperty("codigoAcesso")
    @NotBlank(message = "CODIGO DE ACESSO OBRIGATORIO")
    private String codigoAcesso;

    @JsonProperty("endereco")
    @NotBlank(message = "ENDERECO PRINCIPAL OBRIGATORIO")
    private String endereco;

}
