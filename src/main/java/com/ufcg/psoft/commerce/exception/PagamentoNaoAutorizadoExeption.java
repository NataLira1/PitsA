package com.ufcg.psoft.commerce.exception;

public class PagamentoNaoAutorizadoExeption extends CommerceException{
    public PagamentoNaoAutorizadoExeption() {
        super("Pagamento não autorizado");
    }
}
