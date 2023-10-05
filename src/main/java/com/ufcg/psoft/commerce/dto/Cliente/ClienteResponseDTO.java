package com.ufcg.psoft.commerce.dto.Cliente;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDTO {

    @JsonProperty
    private Long id;

    @JsonProperty("nomeCompleto")
    private String nomeCompleto;

    @JsonProperty("usuario")
    private String usuario;

    @JsonProperty("endereco")
    private String endereco;

}
