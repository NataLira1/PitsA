package com.ufcg.psoft.commerce.service.notificacao;

public interface PedidoListener {

    void notificaPedidoEmRota(PedidoEvent pedido);
    void notificaPedidoEntregue(PedidoEvent pedido);
}
