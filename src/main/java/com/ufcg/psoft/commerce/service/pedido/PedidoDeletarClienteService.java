package com.ufcg.psoft.commerce.service.pedido;

@FunctionalInterface
public interface PedidoDeletarClienteService {
    void deletar(Long pedidoId, Long clienteId, String clienteCodigoAcesso);
}
