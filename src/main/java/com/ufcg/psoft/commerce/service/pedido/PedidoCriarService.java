package com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;

@FunctionalInterface
public interface PedidoCriarService {

    PedidoResponseDTO criar(String clienteCodigoAcesso, PedidoPostPutRequestDTO pedidoPostPutRequestDTO);

}
