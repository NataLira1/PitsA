package com.ufcg.psoft.commerce.service.pagamento;

import com.ufcg.psoft.commerce.util.TipoPagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.ufcg.psoft.commerce.util.TipoPagamento.CARTAO_DEBITO;

@Service
public class DescontoV1DeciderService implements DescontoDeciderService {

    @Autowired
    PagamentoV1DebitoService pagamentoV1DebitoService;

    @Autowired
    PagamentoV1PixService pagamentoV1PixService;

    @Autowired
    PagamentoV1CreditoService pagamentoV1CreditoService;


    @Override
    public DescontoService desconto(TipoPagamento tipoPagamento) {
        switch (tipoPagamento) {
            case CARTAO_DEBITO -> {
                return pagamentoV1DebitoService;
            }
            case PIX -> {
                return pagamentoV1PixService;
            }
            case CARTAO_CREDITO -> {
                return pagamentoV1CreditoService;
            }
        }
        return null;
    }
}
