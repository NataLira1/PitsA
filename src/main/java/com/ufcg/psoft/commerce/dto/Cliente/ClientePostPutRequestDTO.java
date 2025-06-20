package com.ufcg.psoft.commerce.dto.Cliente;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "Nome obrigatorio")
    private String nomeCompleto;

    @JsonProperty("email")
    private String email;

    @JsonProperty("usuario")
    @NotBlank(message = "usuario obrigatorio")
    private String usuario;

    @JsonProperty("codigoAcesso")
    @NotBlank(message = "Codigo de acesso obrigatorio")
    @Size(min=6, max = 6, message = "Codigo de acesso deve ter exatamente 6 digitos numericos")
    @Pattern(regexp = "\\d{6}", message = "Codigo de acesso deve ter exatamente 6 digitos numericos")
    private String codigoAcesso;

    @JsonProperty("endereco")
    @NotBlank(message = "Endereco obrigatorio")
    private String endereco;

}
