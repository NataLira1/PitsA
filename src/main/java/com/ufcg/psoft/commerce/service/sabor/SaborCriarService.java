package com.ufcg.psoft.commerce.service.sabor;


import com.ufcg.psoft.commerce.dto.sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.sabor.SaborResponseDTO;

@FunctionalInterface
public interface SaborCriarService {
    public SaborResponseDTO criar(Long idEstabelecimento, String codigoAcesso, SaborPostPutRequestDTO saborPostPutRequestDTO);
}
