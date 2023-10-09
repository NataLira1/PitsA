package com.ufcg.psoft.commerce.service.pedido;

@FunctionalInterface
public interface PedidoDeletarTodosClienteService {
    void deletarTodos(Long clienteId, String clienteCodigoAcesso);
}
