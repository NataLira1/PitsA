package com.ufcg.psoft.commerce.service.EstabelecimentoServices;

import com.ufcg.psoft.commerce.models.Estabelecimento;

import java.util.List;

public interface EstabelecimentoBuscarService {


    List<Estabelecimento> getAll();

    Estabelecimento getOne(Long id);
}
