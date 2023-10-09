package com.ufcg.psoft.commerce.service.cliente;

import com.ufcg.psoft.commerce.dto.sabor.SaborInteresseResponseDTO;

@FunctionalInterface
public interface ClienteInteresseService {
    public SaborInteresseResponseDTO salvarInteresse(Long id, String codigoAcesso, Long saborId);
}
