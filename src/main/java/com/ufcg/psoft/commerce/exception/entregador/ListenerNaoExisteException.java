package com.ufcg.psoft.commerce.exception.entregador;

import com.ufcg.psoft.commerce.exception.CommerceException;

public class ListenerNaoExisteException extends CommerceException {
    public ListenerNaoExisteException() {
        super("O listener consultado nao existe!");
    }
}
