package com.ufcg.psoft.commerce.service.EstabelecimentoServices;

import com.ufcg.psoft.commerce.models.Sabor;

import java.util.List;
import java.util.Set;


public interface EstabelecimentoBuscarCardapioService {

    Set<Sabor> getCardapio(Long id, String codigo);

    Set<Sabor> getCardapioPorTipo(Long id, String codigo, String tipo);
}
