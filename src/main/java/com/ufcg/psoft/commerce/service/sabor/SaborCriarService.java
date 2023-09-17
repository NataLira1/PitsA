package com.ufcg.psoft.commerce.service.sabor;


import com.ufcg.psoft.commerce.dto.sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.models.Sabor;

@FunctionalInterface
public interface SaborCriarService {
    public Sabor criar(Long idEstabelecimento, String codigoAcesso, SaborPostPutRequestDTO saborPostPutRequestDTO);
}
