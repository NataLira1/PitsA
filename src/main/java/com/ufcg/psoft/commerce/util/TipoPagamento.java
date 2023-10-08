package com.ufcg.psoft.commerce.util;

import jakarta.persistence.Enumerated;
import lombok.*;

@AllArgsConstructor
@Getter
public enum TipoPagamento {

    @Enumerated
    CARTAO_CREDITO(0.0),
    @Enumerated
    CARTAO_DEBITO(0.025),
    @Enumerated
    PIX(0.05);

    private final double desconto;
}
