package com.ufcg.psoft.commerce.service.entregador;

import com.ufcg.psoft.commerce.models.Entregador;

public interface EntregadorFilaService {

    void adicionarFila(Entregador entregador);

    Entregador poll();

    int size();
}
