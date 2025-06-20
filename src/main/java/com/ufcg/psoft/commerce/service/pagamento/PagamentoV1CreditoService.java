package com.ufcg.psoft.commerce.service.pagamento;

import org.springframework.stereotype.Service;

@Service
public class PagamentoV1CreditoService implements DescontoService {

    @Override
    public double calcularDesconto(double valorTotal) {
        return 0.0;
    }
}
