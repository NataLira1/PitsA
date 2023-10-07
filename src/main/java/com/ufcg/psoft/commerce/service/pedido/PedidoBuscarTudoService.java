package com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;

import java.util.List;


public interface PedidoBuscarTudoService {

    List<PedidoResponseDTO> BuscarTodos(String codigoAcesso);

}
