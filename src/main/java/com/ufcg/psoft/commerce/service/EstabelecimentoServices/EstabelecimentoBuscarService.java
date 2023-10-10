package com.ufcg.psoft.commerce.service.EstabelecimentoServices;

import com.ufcg.psoft.commerce.dto.Estabelecimento.EstabelecimentoResponseGetDTO;

import java.util.List;

public interface EstabelecimentoBuscarService {


    List<EstabelecimentoResponseGetDTO> getAll();

    EstabelecimentoResponseGetDTO getOne(Long id);
}
