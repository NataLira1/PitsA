package com.ufcg.psoft.commerce.service.notificacao;

import com.ufcg.psoft.commerce.exception.entregador.ListenerNaoExisteException;
import com.ufcg.psoft.commerce.models.Pedido;

import java.util.Collection;
import java.util.HashSet;

public class PedidoObserver {

    Collection<PedidoListener> listeners = new HashSet<>();

    public void pedidoEmRota(Pedido pedidoReal) {
        PedidoEvent pedido = new PedidoEvent();
        pedido.setEntregador(pedidoReal.getEntregador());
        disparaPedidoEmRota(pedido);
    }
    public void pedidoEntregue(){
        PedidoEvent pedido = new PedidoEvent();
        disparaPedidoEntregue(pedido);
    }

    public void adicionaListener(PedidoListener listener){
        listeners.add(listener);
    }
    public void removeListener(PedidoListener listener){
        if (!listeners.contains(listener)) {
            throw new ListenerNaoExisteException();
        }
        listeners.remove(listener);
    }

    private void disparaPedidoEmRota(PedidoEvent pedido) {
        for (PedidoListener listener : listeners) {
            listener.notificaPedidoEmRota(pedido);
        }
    }

    private void disparaPedidoEntregue(PedidoEvent pedido) {
        for (PedidoListener listener: listeners) {
            listener.notificaPedidoEntregue(pedido);
        }
    }

}
