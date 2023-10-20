package com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;

@FunctionalInterface
public interface PedidoProntoService {

    PedidoResponseDTO finalizado(Long pedidoId, Long estabelecimentoId, String codigoAcessoEstabelecimento);

}
