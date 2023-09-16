package com.ufcg.psoft.commerce.service.EstabelecimentoServices;


import com.ufcg.psoft.commerce.dto.Estabelecimento.EstabelecimentoPatchCodigoDTO;
import com.ufcg.psoft.commerce.models.Estabelecimento;

@FunctionalInterface
public interface EstabelecimentoPatchCodigoService {



    Estabelecimento atualizarEmail(Long id, String codigo, EstabelecimentoPatchCodigoDTO estabelecimentoPatchCodigoDTO);
}
