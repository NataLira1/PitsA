package com.ufcg.psoft.commerce.util;

import lombok.*;

@AllArgsConstructor
@Getter
public enum TipoPagamento {

    CARTAO_CREDITO(0.0),
    CARTAO_DEBITO(0.025),
    PIX(0.05);

    private final double desconto;
}
