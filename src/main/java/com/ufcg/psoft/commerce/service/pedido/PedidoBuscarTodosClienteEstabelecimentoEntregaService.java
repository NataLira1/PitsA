package com.ufcg.psoft.commerce.service.pedido;

import java.util.List;

import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;

@FunctionalInterface
public interface PedidoBuscarTodosClienteEstabelecimentoEntregaService {
    List<PedidoResponseDTO> buscarTodos(Long clienteId, Long estabelecimentoId, String status, String clienteCodigoAcesso);
}
