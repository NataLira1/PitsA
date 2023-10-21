package com.ufcg.psoft.commerce.exception;

public class PedidoNaoPodeSerCanceladoException extends CommerceException  {

    public PedidoNaoPodeSerCanceladoException(){
        super("O pedido já está pronto e não pode ser cancelado");
    }
    
}
