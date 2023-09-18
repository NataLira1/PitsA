package com.ufcg.psoft.commerce.service.entregador;

import com.ufcg.psoft.commerce.dto.Entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Entregador.EntregadorResponseDTO;
import com.ufcg.psoft.commerce.models.Entregador;

@FunctionalInterface
public interface EntregadorEditarService {
    EntregadorResponseDTO editarEntregador(Entregador entregador, EntregadorPostPutRequestDTO novoEntregador);
}
