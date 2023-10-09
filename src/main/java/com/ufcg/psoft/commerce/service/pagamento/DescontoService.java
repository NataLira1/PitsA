package com.ufcg.psoft.commerce.service.pagamento;

import com.ufcg.psoft.commerce.util.TipoPagamento;

@FunctionalInterface
public interface DescontoService {

    double calcularDesconto(double valorTotal);
}
