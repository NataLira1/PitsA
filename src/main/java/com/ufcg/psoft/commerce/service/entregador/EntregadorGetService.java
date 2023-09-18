package com.ufcg.psoft.commerce.service.entregador;

import com.ufcg.psoft.commerce.models.Entregador;

@FunctionalInterface
public interface EntregadorGetService {
    Entregador getEntregador(Long id);
}
