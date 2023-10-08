package com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;

import java.util.List;

@FunctionalInterface
public interface PedidoBuscarTodosClientesEstabelecimentoService {

    List<PedidoResponseDTO> buscarTodos(Long clienteId, Long estabelecimentoId, String clienteCodigoAcesso);

}
