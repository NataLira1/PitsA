package com.ufcg.psoft.commerce.service.notificacao;

import java.util.Collection;
import java.util.HashSet;

public class PedidoObserver {

    Collection<PedidoListener> listeners = new HashSet<>();

    public void pedidoEmRota() {
        disparaPedidoEmRota();
    }
    public void pedidoEntregue(){
        disparaPedidoEntregue();
    }

    public void adicionaListener(PedidoListener listener){
        if (!listeners.contains(listener)) {
            //To-do
        } else {
            listeners.add(listener);
        }
    }
    public void removeListener(PedidoListener listener){
        if (!listeners.contains(listener)) {
            //To-do
        }
        listeners.remove(listener);
    }

    private void disparaPedidoEmRota() {
        PedidoEvent pedido = PedidoEvent.builder().entregador(super.getEntregador()).build();
        for (PedidoListener listener : listeners) {
            listener.notificaPedidoEmRota(pedido);
        }
    }
    private void disparaPedidoEntregue() {
        PedidoEvent pedido = PedidoEvent.builder().entregador(super.getEntregador()).build();
        for (PedidoListener listener: listeners) {
            listener.notificaPedidoEntregue(pedido);
        }
    }

}
