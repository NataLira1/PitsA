package com.ufcg.psoft.commerce.exception;

public class EntregadorNaoEncontradoException extends CommerceException {

    public EntregadorNaoEncontradoException() {
        super("O entregador consultado nao existe!");
    }
}
