package com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoPutRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;

@FunctionalInterface
public interface PedidoAtualizarService {
    PedidoResponseDTO atualizar(Long pedidoId, String clienteCodigoAcesso, PedidoPutRequestDTO pedidoPutRequestDTO);
}
