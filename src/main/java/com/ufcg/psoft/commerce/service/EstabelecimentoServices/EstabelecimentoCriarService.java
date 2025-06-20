package com.ufcg.psoft.commerce.service.EstabelecimentoServices;

import com.ufcg.psoft.commerce.dto.Estabelecimento.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Estabelecimento.EstabelecimentoPostResponseDTO;
import com.ufcg.psoft.commerce.models.Estabelecimento;

@FunctionalInterface
public interface EstabelecimentoCriarService {

    EstabelecimentoPostResponseDTO criar(EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO);

}
