package com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;

@FunctionalInterface
public interface PedidoConfirmarPreparoService {

    PedidoResponseDTO confirmarPreparo(Long pedidoId, Long estabelecimentoId, String codigoAcessoEstabelecimento);

}
