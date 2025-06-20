package com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;

import java.util.List;

@FunctionalInterface
public interface PedidoBuscarTudoEstabelecimentoService {

    List<PedidoResponseDTO> buscarTodos(Long idEstabelecimento, String codigoEstabelecimento);

}
