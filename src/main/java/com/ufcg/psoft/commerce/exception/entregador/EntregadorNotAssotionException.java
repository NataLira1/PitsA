package com.ufcg.psoft.commerce.exception.entregador;

import com.ufcg.psoft.commerce.exception.CommerceException;

public class EntregadorNotAssotionException extends CommerceException {

    public EntregadorNotAssotionException() {
        super("Entregador nao associado a nenhum estabelecimeto!");
    }
}
