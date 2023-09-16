package com.ufcg.psoft.commerce.service.entregador;

import com.ufcg.psoft.commerce.dto.Entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Entregador.EntregadorResponseDTO;
import com.ufcg.psoft.commerce.models.Entregador;

@FunctionalInterface
public interface EntregadorCriarService {
    EntregadorResponseDTO criarEntregador(EntregadorPostPutRequestDTO novoEntregador);
}
