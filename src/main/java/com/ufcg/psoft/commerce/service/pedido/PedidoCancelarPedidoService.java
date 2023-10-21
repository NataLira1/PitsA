package com.ufcg.psoft.commerce.service.pedido;

@FunctionalInterface
public interface PedidoCancelarPedidoService {
    void cancelar(Long pedidoId, String clienteCodigoAcesso);
}
