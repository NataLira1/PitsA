package com.ufcg.psoft.commerce.dto.Cliente;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.models.Pedido;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
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

    @JsonProperty("enderecoPrincipal")
    @NotBlank(message = "ENDERECO PRINCIPAL OBRIGATORIO")
    private String enderecoPrincipal;

    @JsonProperty("pedidos")
    private List<Pedido> pedidos;

}
