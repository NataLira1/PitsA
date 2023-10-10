package com.ufcg.psoft.commerce.service.pedido;

@FunctionalInterface
public interface PedidoDeletarTodosService {
    void deletarTodos(Long clienteEstabelecimentoId, String codigoAcesso);
    
}
