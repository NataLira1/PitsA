package com.ufcg.psoft.commerce.service.EstabelecimentoServices;

import com.ufcg.psoft.commerce.models.Sabor;

import java.util.List;
import java.util.Set;


public interface EstabelecimentoBuscarCardapioService {

    Set<Sabor> getCardapio(Long id);

    Set<Sabor> getCardapioPorTipo(Long id, String tipo);

    Set<Sabor> getCardapioPorDisponibilidade(Long id, Boolean disponivel);
}
