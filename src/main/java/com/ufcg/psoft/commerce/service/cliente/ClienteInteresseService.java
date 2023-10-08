package com.ufcg.psoft.commerce.service.cliente;

import com.ufcg.psoft.commerce.models.Sabor;

import java.util.Set;

@FunctionalInterface
public interface ClienteInteresseService {
    public Sabor salvarInteresse(Long id, String codigoAcesso, Long saborId);
}
