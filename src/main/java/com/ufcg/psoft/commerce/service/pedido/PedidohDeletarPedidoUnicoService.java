package com.ufcg.psoft.commerce.service.pedido;

@FunctionalInterface
public interface PedidohDeletarPedidoUnicoService {
    void deletar(Long pedidoId, Long clienteEstabelecimentoId, String codigoAcesso);
    
}
