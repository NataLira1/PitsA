package com.ufcg.psoft.commerce.service.pedido;

@FunctionalInterface
public interface PedidoDeletarEstabelecimentoService {
    void deletar(Long pedidoId, Long estabelecimentoId, String estabelecimentoCodigoAcesso);
}
