package com.ufcg.psoft.commerce.service.pagamento;

@FunctionalInterface
public interface DescontoService {

    double calcularDesconto(double valorTotal);
}
