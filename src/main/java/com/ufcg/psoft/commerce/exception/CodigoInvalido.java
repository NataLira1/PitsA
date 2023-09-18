package com.ufcg.psoft.commerce.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
public class CodigoInvalido extends CommerceException{
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("message")
    private String message;

    @JsonProperty("errors")
    private List<String> errors;

    public CodigoInvalido() {
        super("Codigo de acesso invalido!");
    }
}
