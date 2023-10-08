package com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;

@FunctionalInterface
public interface PedidoBuscarClienteEstabelecimentoService {

    PedidoResponseDTO buscar(Long clienteId, Long estabelecimentoId, Long pedidoId, String clienteCodigoAcesso);

}
