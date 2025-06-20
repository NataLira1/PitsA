package com.ufcg.psoft.commerce.service.pedido;

@FunctionalInterface
public interface PedidoDeletarTodosEstabelecimentoService{
    void deletarTodos(Long estabelecimentoId, String estabelecimentoCodigoAesso);

}
