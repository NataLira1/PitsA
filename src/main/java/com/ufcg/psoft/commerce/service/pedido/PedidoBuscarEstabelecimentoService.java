package com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;

@FunctionalInterface
public interface PedidoBuscarEstabelecimentoService {

    PedidoResponseDTO buscar(Long pedidoId, Long estabelecimentoId, String estabelecimentoCodigoAcesso);

}
