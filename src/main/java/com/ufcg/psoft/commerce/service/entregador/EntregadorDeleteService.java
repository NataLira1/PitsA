package com.ufcg.psoft.commerce.service.entregador;

import com.ufcg.psoft.commerce.dto.Entregador.EntregadorResponseDTO;

@FunctionalInterface
public interface EntregadorDeleteService {
    void deletar(Long id);
}
