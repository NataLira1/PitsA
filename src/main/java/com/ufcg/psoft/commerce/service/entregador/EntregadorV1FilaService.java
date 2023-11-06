package com.ufcg.psoft.commerce.service.entregador;

import com.ufcg.psoft.commerce.models.Entregador;

import java.util.PriorityQueue;
import java.util.Queue;

public class EntregadorV1FilaService implements EntregadorFilaService {

    Queue<Entregador> disponiveis = new PriorityQueue<>();

    @Override
    public void adicionarFila(Entregador entregador) {
        disponiveis.add(entregador);
    }

    @Override
    public Entregador poll() {
        return disponiveis.poll();
    }

    @Override
    public int size() {
        return disponiveis.size();
    }
}
