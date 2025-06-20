package com.ufcg.psoft.commerce.service.sabor;


import com.ufcg.psoft.commerce.dto.sabor.SaborPutRequestDTO;
import com.ufcg.psoft.commerce.dto.sabor.SaborResponseDTO;

@FunctionalInterface
public interface SaborAtualizarService {
    public SaborResponseDTO atualizar(Long idEstabelecimento, String codigoAcesso, Long id, SaborPutRequestDTO saborPutRequestDTO);
}