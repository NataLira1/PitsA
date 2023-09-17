package com.ufcg.psoft.commerce.service.sabor;


import com.ufcg.psoft.commerce.dto.sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.models.Sabor;

@FunctionalInterface
public interface SaborAtualizarService {
    public Sabor atualizar(Long idEstabelecimento, String codigoAcesso, Long id, SaborPostPutRequestDTO saborPostPutRequestDTO);
}