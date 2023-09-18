package com.ufcg.psoft.commerce.service.sabor;

import com.ufcg.psoft.commerce.dto.sabor.SaborResponseDTO;

@FunctionalInterface
public interface SaborBuscarService {
    public SaborResponseDTO buscar(Long idEstabelecimento, String codigoAcesso, Long id);
}
