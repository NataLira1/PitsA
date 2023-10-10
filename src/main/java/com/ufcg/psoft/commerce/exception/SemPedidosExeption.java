package com.ufcg.psoft.commerce.exception;

public class SemPedidosExeption extends CommerceException{
    public SemPedidosExeption() {
        super("Cliente nao fez pedido");
    }
}
