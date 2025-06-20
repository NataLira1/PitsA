package com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;

@FunctionalInterface
public interface PedidoAssociarEntregadorService {

    PedidoResponseDTO associar(Long pedidoId, Long estabelecimentoId,String codigoAcessoEstabelecimento);

}
