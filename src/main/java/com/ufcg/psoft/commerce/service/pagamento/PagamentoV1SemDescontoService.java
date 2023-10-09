package com.ufcg.psoft.commerce.service.pagamento;

import com.ufcg.psoft.commerce.util.TipoPagamento;
import org.springframework.stereotype.Service;

@Service
public class PagamentoV1SemDescontoService implements DescontoService {

    @Override
    public double calcularDesconto(double valorTotal, TipoPagamento forma) {
        return 0.0;
    }
}
