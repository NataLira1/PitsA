package com.ufcg.psoft.commerce.service.sabor;

import com.ufcg.psoft.commerce.models.Sabor;

@FunctionalInterface
public interface SaborBuscarService {
    public Sabor buscar(Long idEstabelecimento, String codigoAcesso, Long id);
}
