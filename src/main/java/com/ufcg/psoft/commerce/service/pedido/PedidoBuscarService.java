package com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;

@FunctionalInterface
public interface PedidoBuscarService {

    PedidoResponseDTO buscar(Long pedidoId, Long clienteId, String clienteCodigoAcesso);

}
