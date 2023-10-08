package com.ufcg.psoft.commerce.service.pedido;

@FunctionalInterface
public interface PedidoDeletarPedidoUnicoService {
    void deletar(Long pedidoId, Long clienteEstabelecimentoId, String codigoAcesso);
}
