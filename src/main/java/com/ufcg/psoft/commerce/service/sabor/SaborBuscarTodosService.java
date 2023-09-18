package com.ufcg.psoft.commerce.service.sabor;

import java.util.List;

import com.ufcg.psoft.commerce.dto.sabor.SaborResponseDTO;

@FunctionalInterface
public interface SaborBuscarTodosService {
    public List<SaborResponseDTO> buscarTodos(Long idEstabelecimento, String codigoAcesso);
}
