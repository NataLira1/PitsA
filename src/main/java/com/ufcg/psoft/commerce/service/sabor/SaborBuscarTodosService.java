package com.ufcg.psoft.commerce.service.sabor;

import java.util.List;

import com.ufcg.psoft.commerce.models.Sabor;

@FunctionalInterface
public interface SaborBuscarTodosService {
    public List<Sabor> buscarTodos(Long idEstabelecimento, String codigoAcesso);
}
