package com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoPutConfirmarPagamentoRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;

@FunctionalInterface
public interface PedidoConfirmarPagamentoService {
    PedidoResponseDTO confirmar(Long clienteId, String codigoAcessoCliente, Long pedidoId, PedidoPutConfirmarPagamentoRequestDTO pedidoPutConfirmarPagamentoRequestDTO);
}
